package com.sparta.hotbody.diet.dto;

import com.sparta.hotbody.diet.entity.Diet;
import lombok.Getter;

@Getter
public class FoodOfDietRequestDto {
  String foodName;
  String factory;
  String supply;
  String energy;
  String protein;
  String fat;
  String carbohydrate;
  String sugar;
  String diet;

}
