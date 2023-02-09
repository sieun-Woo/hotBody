package com.sparta.hotbody.user.repository;


import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.TrainerLike;
import com.sparta.hotbody.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerLikeRepository extends JpaRepository<TrainerLike, Long> {
  Optional<TrainerLike> findByUserAndTrainer(User user, Trainer trainer);
  Optional<Trainer> findByTrainerId(Long trainerId);
}
