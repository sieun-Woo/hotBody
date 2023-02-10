package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainerResponseDto {
  private String introduce;

  public TrainerResponseDto(Trainer trainer){
    this.introduce = trainer.getIntroduce();
  }

}