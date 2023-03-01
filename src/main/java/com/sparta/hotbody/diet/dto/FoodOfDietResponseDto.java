package com.sparta.hotbody.diet.dto;

import com.sparta.hotbody.diet.entity.FoodOfDiet;
import lombok.Getter;

@Getter
public class FoodOfDietResponseDto {

  String foodName;
  String factory;
  String supply;
  String energy;
  String protein;
  String fat;
  String carbohydrate;
  String sugar;

  public FoodOfDietResponseDto(FoodOfDiet foodOfDiet) {
    this.foodName = foodOfDiet.getFoodName();
    this.factory = foodOfDiet.getFactory();
    this.supply = foodOfDiet.getSupply();
    this.energy = foodOfDiet.getEnergy();
    this.protein = foodOfDiet.getProtein();
    this.fat = foodOfDiet.getFat();
    this.carbohydrate = foodOfDiet.getCarbohydrate();
    this.sugar = foodOfDiet.getSugar();
  }
}
