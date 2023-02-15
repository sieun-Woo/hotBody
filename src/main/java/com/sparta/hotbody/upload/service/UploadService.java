package com.sparta.hotbody.upload.service;


import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.repository.ImageRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadService {

  private final ImageRepository imageRepository;

  @Value("${file.dir}")
  private String fileDir;

  // 이미지 저장된 주소
  public String getFullPath(String filename) {
    return fileDir + filename;
  }

  public Image storeFile(MultipartFile multipartFile) throws IOException {
    if(multipartFile.isEmpty()) {
      return null;
    }

    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);
    multipartFile.transferTo(new File(getFullPath(storeFileName)));
    return imageRepository.save(new Image(originalFilename, storeFileName));

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


}
