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
  private String nickname;
  private String introduce;
  private Integer isPromoted;

  public TrainerResponseDto(Trainer trainer){
    this.nickname = trainer.getUser().getNickname();
    this.introduce = trainer.getIntroduce();
    this.isPromoted = trainer.getIsPromoted();
  }

}