package com.sparta.hotbody.user.entity;

import static javax.persistence.FetchType.LAZY;

import com.sparta.hotbody.user.entity.Trainer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TrainerLike {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Trainer_Like_Id")
  private Long id;

  @Column
  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Trainer_ID")
  private User trainer;

  public TrainerLike(Long userId, User trainer){
    this.userId = userId;
    this.trainer = trainer;
  }
}
