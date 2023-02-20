package com.sparta.hotbody.user.dto;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserProfileRequestDto {
  private int height;
  private int weight;
  private String region;

}
