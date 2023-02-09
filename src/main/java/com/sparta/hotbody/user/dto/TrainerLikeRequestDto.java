package com.sparta.hotbody.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrainerLikeRequestDto {

  private String username;
  private Long trainerId;

  public TrainerLikeRequestDto(String username, Long trainerId) {
    this.username = username;
    this.trainerId = trainerId;
  }
}

