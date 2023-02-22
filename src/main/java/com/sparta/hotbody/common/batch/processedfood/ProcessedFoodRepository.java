package com.sparta.hotbody.common.batch.processedfood;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedFoodRepository extends JpaRepository<ProcessedFood, Long> {
  List<ProcessedFood> findAllByFoodNameContaining(String foodName);
}
