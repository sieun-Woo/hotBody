package com.sparta.hotbody.user.service;

import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileService {
//윈도우가 2개 , 맥이 1개 슬러쉬가 아니라 백슬러쉬 인놈도 있다...!!!!!!!
  private final String location = System.getProperty("user.dir")+"/src/main/resources/static";
  public void upload(MultipartFile file, String fineName){
    try{
      file.transferTo(new File(location + fineName + ".jpg"));
    }catch(IOException e){
      log.error(e.getMessage());
    }
  }
  public void delete(String filename){
    new File(location + filename).delete();
  }
}