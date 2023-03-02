package com.sparta.hotbody.user.entity;

import static javax.persistence.FetchType.LAZY;

import com.sparta.hotbody.user.entity.Trainer;
import javax.persistence.Column;
import javax.persistence.Entity;
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

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "User_ID")
  private User user;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "Trainer_ID")
  private User trainer;

  public TrainerLike(User user, User trainer){
    this.user = user;
    this.trainer = trainer;
  }
}
