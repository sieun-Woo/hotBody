package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.DietResponseDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface DietService {

  public ResponseEntity<String> createDiet(UserDetails userDetails);

  DietResponseDto addFood(Long dietId, Long foodId);

  DietResponseDto readFood(Long dietId, UserDetails userDetails);

  DietResponseDto removeFood(Long dietId, Long foodId);
}
