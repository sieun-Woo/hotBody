package com.sparta.hotbody.diet.conrtroller;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.service.DietServiceImpl;
import com.sparta.hotbody.diet.service.FoodServiceImpl;
import java.util.List;
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
  private final FoodServiceImpl foodServiceImpl;

  @GetMapping("/diet")
  public List<FoodResponseDto> searchFood(@RequestParam String foodType, @RequestParam String searchFoodText) {
    try {
      List<FoodResponseDto> foodResponseDtoList = foodServiceImpl.searchFood(foodType, searchFoodText);
      return foodResponseDtoList;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }
}
