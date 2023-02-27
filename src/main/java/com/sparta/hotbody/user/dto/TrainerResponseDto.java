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
  private int gender;
  private int age;
  private String region;
  private String email;
  private String introduce;
  private int isPromoted;

  public TrainerResponseDto(Trainer trainer) {
    this.id = trainer.getId();
    this.nickname = trainer.getUser().getNickname();
    this.gender = trainer.getUser().getGender();
    this.age = trainer.getUser().getAge();
    this.region = trainer.getUser().getRegion();
    this.email = trainer.getUser().getEmail();
    this.introduce = trainer.getIntroduce();
    this.isPromoted = trainer.getIsPromoted();
  }
}