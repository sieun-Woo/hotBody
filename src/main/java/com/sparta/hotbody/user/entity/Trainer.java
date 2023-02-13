package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Trainer extends TimeStamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Trainer_ID")
  private Long id;
  @ManyToOne
  private User user;
  private String introduce;
  @Column
  private Integer isPromoted;


  public Trainer(TrainerRequestDto requestDto, User user){
    this.user = user;
    this.introduce = requestDto.getIntroduce();
    this.isPromoted = 0;
  }

  public void isPromoted(Integer isPromoted) {
    this.isPromoted = isPromoted;
  }

  public void promote() {
    this.isPromoted = 1;
  }

}