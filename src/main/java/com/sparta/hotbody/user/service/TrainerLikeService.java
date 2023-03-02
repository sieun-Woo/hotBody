package com.sparta.hotbody.user.service;

import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostLike;
import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.TrainerLike;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
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
  private final UserRepository userRepository;

  @Transactional
  public void addLike(Long trainerId, User user) {
    User trainer = userRepository.findById(trainerId).orElseThrow(
        () -> new IllegalArgumentException("트레이너 존재 유무 확인")
    );

    if (trainerLikeRepository.existsByUserIdAndTrainerId(user.getId(), trainerId)) {
      throw new IllegalArgumentException("이미 좋아요 버튼을 눌렀습니다.");
    }

    if(trainer.getRole().equals(UserRole.TRAINER)){
      TrainerLike trainerLike = new TrainerLike(user, trainer);
      trainerLikeRepository.saveAndFlush(trainerLike);
    }

  }

  // 트레이너 좋아요 취소
  @Transactional
  public void cancelLike(Long trainerId, User user) {
    User trainer = userRepository.findById(trainerId).orElseThrow(
        () -> new IllegalArgumentException("트레이너 존재 유무 확인")
    );
    if (!trainer.getRole().equals(UserRole.TRAINER)) {
      throw new IllegalArgumentException("이미 좋아요가 취소되었습니다.");
    }
    trainerLikeRepository.existsByUserIdAndTrainerId(user.getId(), trainerId);
  }

}
