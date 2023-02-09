package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.IOException;

public interface DietService {

  public FoodResponseDto createDiet(String searchFoodText, String foodType);

  }
