package com.sparta.hotbody;

import com.sparta.hotbody.diet.service.DietServiceImpl;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableBatchProcessing
public class HotBodyApplication {

  public static void main(String[] args) {
    SpringApplication.run(HotBodyApplication.class, args);
  }

}
