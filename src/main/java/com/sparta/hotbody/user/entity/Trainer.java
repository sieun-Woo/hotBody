package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.post.entity.PostLike;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
  @OneToOne
  @JoinColumn(name = "User_ID")
  private User user;
  private String introduce;
  @Column
  private Integer isPromoted;
  @Column
  private int likes = 0;
  // 트레이너 , 트레이너 좋아요의 연관 관계(1 : N)
  @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrainerLike> trainerLikeList = new ArrayList<>();

  public Trainer(TrainerRequestDto requestDto, User user){
    this.user = user;
    this.introduce = requestDto.getIntroduce();
    this.isPromoted = 0;
  }

  public void promote() {
    this.isPromoted = 1;
  }

  public void plusLikes() { this.likes += 1; }

  public void minusLikes() {
    this.likes -= 1;
  }
}