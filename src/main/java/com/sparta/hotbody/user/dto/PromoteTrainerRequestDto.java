package com.sparta.hotbody.user.dto;

import java.util.Date;
import javax.persistence.Column;
import lombok.Getter;

@Getter
public class PromoteTrainerRequestDto {
  private String username;
  private String password;
  private String trainerName;
  private String nickname;
  private Integer gender;
  private String age;
  private String introduce;
}