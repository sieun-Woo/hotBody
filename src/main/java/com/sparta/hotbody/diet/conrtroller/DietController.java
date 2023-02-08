package com.sparta.hotbody.diet.conrtroller;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.service.DietServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DietController {

  private final DietServiceImpl dietServiceImpl;

  @GetMapping("/diet")
  public FoodResponseDto searchFood(@RequestParam String foodType, @RequestParam String searchFoodText) {
    try {
      FoodResponseDto foodResponseDto = dietServiceImpl.searchFood(searchFoodText, foodType);
      return foodResponseDto;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }
}
