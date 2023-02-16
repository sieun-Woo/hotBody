package com.sparta.hotbody.user.service;

import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostLike;
import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.TrainerLike;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.TrainerLikeRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class TrainerLikeService {

  private final TrainerLikeRepository trainerLikeRepository;
  private final PromoteRepository promoteRepository;

  public void addLike(Long trainerId, User user) {
    Trainer trainer = promoteRepository.findById(trainerId).orElseThrow(
        () -> new IllegalArgumentException("트레이너 존재 유무 확인")
    );
    if (trainerLikeRepository.existsByTrainerIdAndUserId(trainerId, user.getId())) {
      throw new IllegalArgumentException("이미 좋아요 버튼을 눌렀습니다.");
    }
    TrainerLike trainerLike = new TrainerLike(trainer, user);
    trainer.plusLikes();
    trainerLikeRepository.save(trainerLike);
  }

  // 7. 트레이너 좋아요 취소
  @org.springframework.transaction.annotation.Transactional
  public void cancelLike(Long trainerId, User user) {
    Trainer trainer = promoteRepository.findById(trainerId).orElseThrow(
        () -> new IllegalArgumentException("트레이너 존재 유무 확인")
    );
    if (!trainerLikeRepository.existsByTrainerIdAndUserId(trainerId, user.getId())) {
      throw new IllegalArgumentException("이미 좋아요가 취소되었습니다.");
    }
//    PostLike postLike = new PostLike(post, user);
    trainerLikeRepository.deleteByTrainerIdAndUserId(trainerId, user.getId());
    trainer.minusLikes();
  }

}
