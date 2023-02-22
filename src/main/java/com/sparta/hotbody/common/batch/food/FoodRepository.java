package com.sparta.hotbody.common.batch.food;


import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProducts;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
  List<Food> findAllByFoodNameContaining(String foodName);

  Page<Food> findAllByFoodNameContaining(String foodName, Pageable pageable);
}
