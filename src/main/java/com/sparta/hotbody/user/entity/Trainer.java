package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.user.dto.PromoteTrainerRequestDto;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
  @Column(nullable = false)
  private String trainerName; // 트레이너용 아이디
  @Column(nullable = false)
  private String password; //트레이너용 비밀번호
  @ManyToOne
  private User user;
  private String introduce;
  private String nickname; // 트레이너용 닉네임
  private Integer gender;
  @Column(nullable = false)
  private Integer likeViewCount;
  @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
  Set<TrainerLike> trainerLikes = new HashSet<>();

  @Column
  private Integer isPromoted;

  // 일반 유저로 아이디 쓰고 싶을 때 , 트레이너로 활동 할 때
  // 이렇게 권한 별로 다른 아이디를 가질 수 있게끔 설정

  public Trainer(PromoteTrainerRequestDto requestDto, User user){
    this.trainerName = requestDto.getTrainerName();
    this.password = requestDto.getPassword();
    this.user = user;
    this.introduce = requestDto.getIntroduce();
    this.nickname = requestDto.getNickname();
    this.gender = requestDto.getGender();
    this.likeViewCount = trainerLikes.size();
  }

  public void isPromoted(Integer isPromoted) {
    this.isPromoted = isPromoted;
  }
}