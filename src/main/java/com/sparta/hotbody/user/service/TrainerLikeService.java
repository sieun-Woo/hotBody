package com.sparta.hotbody.user.service;

import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.TrainerLike;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.TrainerLikeRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class TrainerLikeService {
  private final TrainerLikeRepository trainerLikeRepository;

  public boolean addLike(User user, Long trainerId) {
    Trainer trainer = trainerLikeRepository.findByTrainerId(trainerId).orElseThrow(
        () -> new IllegalArgumentException("트레이너를 다시 확인 해주세요.")
    );

    //좋아요 중복 방지
    if (isNotAlreadyLike(user, trainer)) {
      TrainerLike trainerLike = new TrainerLike();
      trainerLike.setUser(user);
      trainerLike.setTrainer(trainer);
      trainerLikeRepository.save(trainerLike);
      return true;
    } else {
      TrainerLike like = trainerLikeRepository.findByUserAndTrainer(user, trainer).orElseThrow(
          () -> new IllegalArgumentException("이미 좋아요를 누르셨습니다.")
      );
      trainerLikeRepository.delete(like);
      return false;
    }
  }

  private boolean isNotAlreadyLike(User user, Trainer trainer) {
    return trainerLikeRepository.findByUserAndTrainer(user, trainer).isEmpty();
  }

}
