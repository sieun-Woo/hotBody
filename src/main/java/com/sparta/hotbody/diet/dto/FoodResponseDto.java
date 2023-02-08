package com.sparta.hotbody.diet.dto;

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
  public FoodResponseDto(Row row) {
    this.foodName = row.getCell(5).toString(); // 음식 이름
    this.amountOfFood = row.getCell(11).toString(); // 음식 양
    this.energy = row.getCell(15).toString(); // 에너지
    this.carbohydrate = row.getCell(19).toString(); // 탄수화물
    this.protein = row.getCell(17).toString(); // 단백질
    this.fat = row.getCell(18).toString(); // 지방
  }
}
