package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.IOException;
import java.util.List;

public interface FoodService {
  public List<FoodResponseDto> searchFood(String text, String foodType) throws IOException;

}
