package com.sparta.hotbody.user.repository;


import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.TrainerLike;
import com.sparta.hotbody.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerLikeRepository extends JpaRepository<TrainerLike, Long> {
  boolean existsByUserIdAndTrainerId(Long userId, Long trainerId);

  Optional<TrainerLike> findAllByTrainerId(Long trainerId);
  Optional<TrainerLike> findAllByUserId(Long userid);

  void deleteByUserId(Long userId);
}
