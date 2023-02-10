package com.sparta.hotbody.diet.entity;

import com.sparta.hotbody.diet.dto.FoodRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DIET_ID")
  private Diet diet;
  @Column
  private String foodName; // 음식 이름

  @Column
  private String amountOfFood; // 음식 양

  @Column
  private String energy; // 에너지

  @Column
  private String carbohydrate; // 탄수화물

  @Column
  private String protein; // 단백질

  @Column
  private String fat; // 지방

  public Food(FoodRequestDto foodRequestDto) {
    this.foodName = foodRequestDto.getFoodName();
    this.amountOfFood = foodRequestDto.getAmountOfFood();
    this.energy = foodRequestDto.getEnergy();
    this.carbohydrate = foodRequestDto.getCarbohydrate();
    this.protein = foodRequestDto.getProtein();
    this.fat = foodRequestDto.getFat();
  }

  public void setDiet(Diet diet) {
    this.diet = diet;

    if(!diet.getFoods().contains(this)) {
      diet.getFoods().add(this);
    }
  }
}
