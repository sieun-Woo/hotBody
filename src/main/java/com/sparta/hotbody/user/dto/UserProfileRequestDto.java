package com.sparta.hotbody.user.dto;

import lombok.Getter;


@Getter
public class UserProfileRequestDto {
  private int height;
  private int weight;
  private String region;
  private String nickname;
}
