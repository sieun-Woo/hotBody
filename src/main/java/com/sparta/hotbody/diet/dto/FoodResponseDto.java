package com.sparta.hotbody.diet.dto;

import java.io.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;

@NoArgsConstructor
@Getter
public class FoodResponseDto {

  String foodName; // 음식 이름
  String amountOfFood; // 음식 양
  String energy; // 에너지
  String carbohydrate; // 탄수화물
  String protein; // 단백질
  String fat; // 지방

  public FoodResponseDto(String foodType, String[] row) {
    switch (foodType) {
      case ("농축산물"):
        this.foodName = row[5]; // 음식 이름
        this.amountOfFood = row[10]; // 음식 양
        this.energy = row[11]; // 에너지
        this.protein = row[13]; // 단백질
        this.fat = row[14]; // 지방
        this.carbohydrate = row[16]; // 탄수화물
        break;

      case ("수산물"):
        this.foodName = row[5]; // 음식 이름
        this.amountOfFood = row[11]; // 음식 양
        this.energy = row[13]; // 에너지
        this.protein = row[15]; // 단백질
        this.fat = row[16]; // 지방
        this.carbohydrate = row[18]; // 탄수화물
        break;

      case ("음식"):
        this.foodName = row[5]; // 음식 이름
        this.amountOfFood = row[11]; // 음식 양
        this.energy = row[15]; // 에너지
        this.protein = row[17]; // 단백질
        this.fat = row[18]; // 지방
        this.carbohydrate = row[19]; // 탄수화물
        break;

      case ("가공음식"):
        this.foodName = row[5]; // 음식 이름
        this.amountOfFood = row[10]; // 음식 양
        this.energy = row[14]; // 에너지
        this.protein = row[16]; // 단백질
        this.fat = row[17]; // 지방
        this.carbohydrate = row[18]; // 탄수화물
        break;
    }
  }

}

