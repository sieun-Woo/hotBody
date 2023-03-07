package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.common.timestamp.TimeStamp;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Trainer extends TimeStamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trainer_id")
  private Long id;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column
  private String introduce;
  @Column
  private Integer isPromoted;

  public Trainer(TrainerRequestDto requestDto, User user){
    this.user = user;
    this.introduce = requestDto.getIntroduce();
    this.isPromoted = 0;
  }

  public Trainer(User user, String introduce){
    this.user = user;
    this.introduce = introduce;
    this.isPromoted = 0;
  }

  public void promote() {
    this.isPromoted = 1;
  }
}