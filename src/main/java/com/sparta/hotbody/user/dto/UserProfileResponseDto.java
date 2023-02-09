package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
  private int height;
  private int weight;
  private String region;
  private String image;
  private String nickname;


  private UserProfileResponseDto(User user){
    this.height= user.getHeight();
    this.weight = user.getWeight();
    this.region = user.getRegion();
    this.image = user.getImage();
    this.nickname = user.getNickname();
  }
  public static UserProfileResponseDto from(User user){
    return new UserProfileResponseDto(user);
  }
}