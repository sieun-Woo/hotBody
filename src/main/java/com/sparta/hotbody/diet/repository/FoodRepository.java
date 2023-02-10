package com.sparta.hotbody.diet.repository;

import com.sparta.hotbody.diet.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
