package com.sparta.hotbody;

import com.sparta.hotbody.diet.Service.DietServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotBodyApplication {

  public static void main(String[] args) {
    DietServiceImpl dietServiceImpl = new DietServiceImpl();
    try {
      System.out.println(dietServiceImpl.readExcel().values());
    } catch (Exception e) {
      e.getMessage();
    }
//    SpringApplication.run(HotBodyApplication.class, args);
  }

}
