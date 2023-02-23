package com.sparta.hotbody.diet.dto;

import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProducts;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProducts;
import com.sparta.hotbody.common.batch.food.Food;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFood;
import lombok.Getter;

@Getter
public class FoodResponseDto {

  String foodName = "정보 없음";
  String factory = "정보 없음";
  String oneTimeSupply = "정보 없음";
  String energy = "정보 없음";
  String protein = "정보 없음";
  String fat = "정보 없음";
  String carbohydrate = "정보 없음";
  String sugar = "정보 없음";

  public FoodResponseDto(AgriculturalAndLivestockProducts agriculturalAndLivestockProducts) {
    this.foodName = agriculturalAndLivestockProducts.getFoodName();
    this.oneTimeSupply = agriculturalAndLivestockProducts.getOneTimeSupply();
    this.energy = agriculturalAndLivestockProducts.getEnergy();
    this.protein = agriculturalAndLivestockProducts.getProtein();
    this.fat = agriculturalAndLivestockProducts.getFat();
    this.carbohydrate = agriculturalAndLivestockProducts.getCarbohydrate();
    this.sugar = agriculturalAndLivestockProducts.getSugar();
  }

  public FoodResponseDto(AquaticProducts aquaticProducts) {
    this.foodName = aquaticProducts.getFoodName();
    this.oneTimeSupply = aquaticProducts.getOneTimeSupply();
    this.energy = aquaticProducts.getEnergy();
    this.protein = aquaticProducts.getProtein();
    this.fat = aquaticProducts.getFat();
    this.carbohydrate = aquaticProducts.getCarbohydrate();
  }

  public FoodResponseDto(Food food) {
    this.foodName = food.getFoodName();
    this.oneTimeSupply = food.getOneTimeSupply();
    this.energy = food.getEnergy();
    this.protein = food.getProtein();
    this.fat = food.getFat();
    this.carbohydrate = food.getCarbohydrate();
    this.sugar = food.getSugar();
  }

  public FoodResponseDto(ProcessedFood processedFood) {
    this.foodName = processedFood.getFoodName();
    this.factory = processedFood.getFactory();
    this.oneTimeSupply = processedFood.getOneTimeSupply();
    this.energy = processedFood.getEnergy();
    this.protein = processedFood.getProtein();
    this.fat = processedFood.getFat();
    this.carbohydrate = processedFood.getCarbohydrate();
    this.sugar = processedFood.getSugar();
  }
}
