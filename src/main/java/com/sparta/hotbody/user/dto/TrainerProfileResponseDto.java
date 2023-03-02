package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class TrainerProfileResponseDto {
  private int height;
  private int weight;
  private String region;
  private String nickname;
  private String introduce;

//  public TrainerProfileResponseDto(Trainer trainer){
//    this.height= trainer.getHeight();
//    this.weight = trainer.getWeight();
//    this.region = trainer.getRegion();
//    this.nickname = trainer.getNickname();
//    this.introduce = trainer.getIntroduce();
//  }

  @Builder
  public TrainerProfileResponseDto(int height, int weight, String region, String nickname, String introduce){
    this.height = height;
    this.weight = weight;
    this.region = region;
    this.nickname = nickname;
    this.introduce = introduce;
  }

//  public static TrainerProfileResponseDto from(Trainer trainer){
//    return new TrainerProfileResponseDto(trainer);
//  }

//  public Page<TrainerProfileResponseDto> toDtoPage(Page<Trainer> trainerPage) {
//    Page<TrainerProfileResponseDto> trainerProfileResponseDtoPage = trainerPage
//        .map(t -> TrainerProfileResponseDto.builder()
//            .height(t.getHeight())
//            .weight(t.getWeight())
//            .region(t.getRegion())
//            .introduce(t.getIntroduce())
//            .nickname(t.getNickname())
//            .build());
//    return trainerProfileResponseDtoPage;
//  };
}