package com.sparta.hotbody.diet.dto;

import lombok.Getter;

@Getter
public class FoodRequestDto {

  String foodName; // 음식 이름
  String amountOfFood; // 음식 양
  String energy; // 에너지
  String carbohydrate; // 탄수화물
  String protein; // 단백질
  String fat; // 지방

}
