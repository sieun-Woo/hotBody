package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
  private final int height;
  private final int weight;
  private final String region;
  private final String image;

  private UserProfileResponseDto(User user){
    this.height= user.getHeight();
    this.weight = user.getWeight();
    this.region = user.getRegion();
    this.image = user.getImage();
  }
  public static UserProfileResponseDto from(User user){
    return new UserProfileResponseDto(user);
  }
}