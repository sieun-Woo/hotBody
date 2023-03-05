package com.sparta.hotbody.user.service;

import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class TrainerLikeService {

  private final TrainerLikeRepository trainerLikeRepository;
  private final UserRepository userRepository;

  public ResponseEntity<String> addLike(Long trainerId, User user) {
    User trainer = userRepository.findById(trainerId).orElseThrow(
        () -> new CustomException(ExceptionStatus.TRAINER_IS_NOT_EXIST)
    );
    if (trainerLikeRepository.existsByUserIdAndTrainerId(user.getId(), trainerId)) {
      throw new CustomException(ExceptionStatus.ADDED_LIKE);
    }
    if(trainer.getRole().equals(UserRole.TRAINER)){
      TrainerLike trainerLike = new TrainerLike(user, trainer);
      trainerLikeRepository.save(trainerLike);
    }
    return ResponseEntity.ok("좋아요 눌렀습니다.");
  }

  // 7. 트레이너 좋아요 취소
  public ResponseEntity<String> cancelLike(Long trainerId, User user) {
    User trainer = userRepository.findById(trainerId).orElseThrow(
        () -> new CustomException(ExceptionStatus.TRAINER_IS_NOT_EXIST)
    );
    if (!trainer.getRole().equals(UserRole.TRAINER)) {
      throw new CustomException(ExceptionStatus.CANCELED_LIKE);
    }
    trainerLikeRepository.deleteByUserIdAndTrainerId(user.getId(), trainerId);
    return ResponseEntity.ok("좋아요를 취소하였습니다.");
  }
}
