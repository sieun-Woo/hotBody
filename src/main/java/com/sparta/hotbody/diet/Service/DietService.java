package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.IOException;

public interface DietService {
  public FoodResponseDto searchFood(String text, String foodType) throws IOException;

  public FoodResponseDto saveDiet(String searchFoodText, String foodType);

  }
