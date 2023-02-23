package com.sparta.hotbody.common.batch.processedfood;

import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProducts;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedFoodRepository extends JpaRepository<ProcessedFood, Long> {
  List<ProcessedFood> findAllByFoodNameContaining(String foodName);
  Page<ProcessedFood> findAllByFoodNameContaining(String foodName, Pageable pageable);
}
