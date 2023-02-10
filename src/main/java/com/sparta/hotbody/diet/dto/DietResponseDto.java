package com.sparta.hotbody.diet.dto;

import com.sparta.hotbody.diet.entity.Diet;
import com.sparta.hotbody.diet.entity.Food;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;

@Getter
public class DietResponseDto {

  private List<FoodResponseDto> foods;

  public DietResponseDto(Diet diet) {
    List<FoodResponseDto> foods = new ArrayList<FoodResponseDto>();
    for(Food food : diet.getFoods()) {
      foods.add(new FoodResponseDto(food));
    }
    this.foods = foods;
  }
}
