package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromoteTrainerResponseDto {
  private String introduce;

  public PromoteTrainerResponseDto(Trainer promote){
    this.introduce = promote.getIntroduce();
  }

}