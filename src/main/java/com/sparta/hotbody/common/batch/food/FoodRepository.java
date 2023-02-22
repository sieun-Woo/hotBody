package com.sparta.hotbody.common.batch.food;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
  List<Food> findAllByFoodNameContaining(String foodName);
}
