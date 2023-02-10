package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.DietResponseDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.IOException;
import org.springframework.http.ResponseEntity;

public interface DietService {

  public ResponseEntity<String> createDiet();

  DietResponseDto addFood(Long dietId, Long foodId);

  DietResponseDto readFood(Long dietId);
}
