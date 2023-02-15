package com.sparta.hotbody.upload.controller;

import com.sparta.hotbody.upload.entity.AwsS3;
import com.sparta.hotbody.upload.service.AwsS3Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

  private final AwsS3Service awsS3Service;

  @PostMapping("/resource")
  public AwsS3 upload(@RequestPart("file") MultipartFile multipartFile)
      throws IOException {
    return awsS3Service.upload(multipartFile,"upload");
  }

  @DeleteMapping("/resource")
  public void remove(@RequestBody AwsS3 awsS3) {
    awsS3Service.remove(awsS3);
  }
}
