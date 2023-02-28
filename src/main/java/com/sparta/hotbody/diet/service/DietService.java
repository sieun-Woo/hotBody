package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodOfDietRequestDto;
import com.sparta.hotbody.diet.entity.Diet;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface DietService {

  public Page searchFood(String FoodTyp0e, String searchWord, int page);

  Long saveDiet();

  Diet readDiet(String time);

  ResponseEntity<String> saveFood(FoodOfDietRequestDto foodOfDietRequestDto, Long id);
}
