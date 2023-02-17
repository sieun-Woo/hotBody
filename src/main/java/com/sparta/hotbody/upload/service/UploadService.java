package com.sparta.hotbody.upload.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.repository.ImageRepository;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadService {

  private final ImageRepository imageRepository;

  private final AmazonS3 amazonS3;

  // S3 버킷
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  // 임시 파일
  @Value("${file.dir}")
  private String fileDir;
  // S3 이미지 주소
  @Value("${Resource.Url}")
  private String Resource;

  public String getFullPath(String filename) {
    return fileDir + filename;
  }

  public Image storeFile(MultipartFile multipartFile) throws IOException {
    if (multipartFile.isEmpty()) {
      return null;
    }
    return upload(multipartFile);
  }

  private Image upload(MultipartFile multipartFile) throws IOException {

    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);

    File file = convertMultipartFileToFile(multipartFile, storeFileName);

    String filePath = filePath(file);
    String resourcePath = putS3(file, filePath);
    removeFile(file);

    return imageRepository.save(
        Image
            .builder()
            .uploadFileName(originalFilename)
            .storeFileName(storeFileName)
            .filePath(filePath)
            .resourcePath(resourcePath)
            .build()
    );
  }

  private String createStoreFileName(String originalFilename) {
    String uuid = UUID.randomUUID().toString();
    String ext = extractExt(originalFilename);
    return uuid + "." + ext;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }


  private String filePath(File file) {
    return "image" + "/" + file.getName();
  }

  private String putS3(File uploadFile, String fileName) {
    amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
        .withCannedAcl(CannedAccessControlList.PublicRead));
    return getS3(bucket, fileName);
  }

  private String getS3(String bucket, String fileName) {
    return amazonS3.getUrl(bucket, fileName).toString();
  }

  // 임시파일 삭제
  private void removeFile(File file) {
    file.delete();
  }

  // S3에 업로드를 위한 변환
  public File convertMultipartFileToFile(MultipartFile multipartFile, String storeFileName)
      throws IOException {

    File file = new File(getFullPath(storeFileName));
    multipartFile.transferTo(file);
    return file;
  }

  // S3에서 이미지 리소스 가져오기
  public UrlResource viewImage(String filename) throws MalformedURLException {
    return new UrlResource(Resource + filename);
  }

  // S3에서 이미지 리소스 삭제하기
  public void remove(String resourcePath) {
    Image image = imageRepository.findByResourcePath(resourcePath).get();

    if (!amazonS3.doesObjectExist(bucket, image.getFilePath())) {
      throw new AmazonS3Exception("Object " + image.getFilePath() + " does not exist!");
    }
    amazonS3.deleteObject(bucket, image.getFilePath());
  }

}
