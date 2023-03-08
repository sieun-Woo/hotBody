package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.TrainerLike;
import com.sparta.hotbody.user.entity.UserRole;

public class LikedTrainerResponseDto {

  private Long id;
  private String nickname;
  private Integer gender;
  private Integer age;
  private String introduce;
  private String region;
  private String email;
  private UserRole role;
  private Integer likes;

  public LikedTrainerResponseDto(TrainerLike trainerLike) {

    this.id = trainerLike.getTrainer().getId();
    this.nickname = trainerLike.getTrainer().getNickname();
    this.gender = trainerLike.getTrainer().getGender();
    this.age = trainerLike.getTrainer().getAge();
    this.introduce = trainerLike.getTrainer().getIntroduce();
    this.region = trainerLike.getTrainer().getRegion();
    this.email = trainerLike.getTrainer().getEmail();
    this.role = trainerLike.getTrainer().getRole();
    this.likes = trainerLike.getTrainer().getTrainerLikeList().size();
  }
}
