package com.sparta.hotbody.upload.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
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


    // S3 이미지 주소
    @Value("${Resource.Url}")
    private String Resource;

    public Image storeFile(MultipartFile multipartFile) {
        return upload(multipartFile);
    }

    private Image upload(MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String filePath = filePath(storeFileName);
        String resourcePath = Resource + storeFileName;

        return imageRepository.save(Image.builder().uploadFileName(originalFilename).storeFileName(storeFileName).filePath(filePath).resourcePath(resourcePath).build());
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

    private String filePath(String storeFileName) {
        return "image" + "/" + storeFileName;
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

    public String getImage() {

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1시간
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, "image/008bc7a2-5412-4c11-b24a-04ea13e76502.jpg").withMethod(HttpMethod.GET).withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toExternalForm();
    }

    public String putImage(String storeFileName) {

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1시간
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, "image" + "/" + storeFileName).withMethod(HttpMethod.PUT).withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toExternalForm();
    }
}

