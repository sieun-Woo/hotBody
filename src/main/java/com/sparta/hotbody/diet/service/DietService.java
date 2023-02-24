package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.entity.Diet;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface DietService {

  public Page searchFood(String FoodTyp0e, String searchWord, int page);

  ResponseEntity<String> saveDiet(Diet diet);

  String readDiet(String time);
}
