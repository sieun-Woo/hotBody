package com.sparta.hotbody.user.dto;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileRequestDto {
  private String password;
  private int height;
  private int weight;
  private String region;
  private String image;

}
