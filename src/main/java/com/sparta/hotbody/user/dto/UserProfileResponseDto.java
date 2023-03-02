package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class UserProfileResponseDto {
  private int height;
  private int weight;
  private String region;
  private String nickname;

  public UserProfileResponseDto(User user){
    this.height= user.getHeight();
    this.weight = user.getWeight();
    this.region = user.getRegion();
    this.nickname = user.getNickname();
  }

  @Builder
  public UserProfileResponseDto(int height, int weight, String region, String nickname){
    this.height = height;
    this.weight = weight;
    this.region = region;
    this.nickname = nickname;
  }

  public static UserProfileResponseDto from(User user){
    return new UserProfileResponseDto(user);
  }

  public Page<UserProfileResponseDto> toDtoPage(Page<User> userPage) {
    Page<UserProfileResponseDto> userProfileResponseDtoPage = userPage
        .map(m -> UserProfileResponseDto.builder()
            .height(m.getHeight())
            .weight(m.getWeight())
            .region(m.getRegion())
            .nickname(m.getNickname())
            .build());
    return userProfileResponseDtoPage;
  };
}