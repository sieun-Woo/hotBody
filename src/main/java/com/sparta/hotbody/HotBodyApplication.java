package com.sparta.hotbody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HotBodyApplication {

  public static void main(String[] args) {
    SpringApplication.run(HotBodyApplication.class, args);
  }

}
