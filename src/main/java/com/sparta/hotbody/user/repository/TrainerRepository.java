package com.sparta.hotbody.user.repository;

import com.sparta.hotbody.user.entity.Trainer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
  Optional<Trainer> findByIdAndUsername(Long id, String username);
  Optional<Trainer> findByUsername(String username);

  Page<Trainer> findAll(Pageable pageable);

}
