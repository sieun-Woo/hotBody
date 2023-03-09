package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.User;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainerResponseDto {

  private Long id;
  private String nickname;
  private Integer gender;
  private int age;
  private String region;
  private String email;
  private String introduce;
  private int isPromoted;
  private Integer likes;

  public TrainerResponseDto(User user) {
    this.id = user.getTrainer().getId();
    this.nickname = user.getNickname();
    this.gender = user.getGender();
    this.age = user.getAge();
    this.region = user.getRegion();
    this.email = user.getEmail();
    this.introduce = user.getTrainer().getIntroduce();
    this.isPromoted = user.getTrainer().getIsPromoted();
    this.likes = user.getTrainerLikeList().size();
  }
}