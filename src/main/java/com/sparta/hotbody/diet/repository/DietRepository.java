package com.sparta.hotbody.diet.repository;

import com.sparta.hotbody.diet.entity.Diet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {
  Optional<Diet> findByTime(String time);
}
