package com.sparta.hotbody.user.entity;

import static javax.persistence.FetchType.LAZY;

import com.sparta.hotbody.post.entity.Post;
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
    @Column(name = "postLikeId")
    private Long trainerLikeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trainerId")
    private Trainer trainer;

    // 연관 관계 편의 메소드
    public void setUser(User user){
      this.user = user;
    }
    public void setTrainer(Trainer trainer){
      this.trainer = trainer;
    }

}
