package com.sparta.hotbody.diet.dto;

import com.sparta.hotbody.diet.entity.Diet;
import com.sparta.hotbody.diet.entity.Food;
import java.time.LocalDateTime;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
public class DietResponseDto {


  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private List<FoodResponseDto> foods;

  public DietResponseDto(Diet diet) {

    this.createdAt = diet.getCreatedAt();

    this.modifiedAt = diet.getModifiedAt();

    List<FoodResponseDto> foods = new ArrayList<FoodResponseDto>();
    for (Food food : diet.getFoods()) {
      foods.add(new FoodResponseDto(food));
    }
    this.foods = foods;
  }
}
