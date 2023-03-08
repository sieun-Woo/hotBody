package com.sparta.hotbody.user.entity;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trainer_id")
  private User trainer;

  @Column
  private Long userId;

  public TrainerLike(User trainer, Long userId){
    this.trainer = trainer;
    this.userId = userId;
  }
}
