package com.sparta.hotbody.diet.entity;

import com.sparta.hotbody.diet.dto.FoodOfDietRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class FoodOfDiet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;
  @Column
  String foodName;
  @Column
  String factory;
  @Column
  String supply;
  @Column
  String energy;
  @Column
  String protein;
  @Column
  String fat;
  @Column
  String carbohydrate;
  @Column
  String sugar;
  @ManyToOne
  @JoinColumn(name = "DEIT_ID")
  private Diet diet;

  public void setDiet(Diet diet) {
    this.diet = diet;
  }

  public FoodOfDiet(FoodOfDietRequestDto foodOfDietRequestDto) {
    this.foodName = foodOfDietRequestDto.getFoodName();
    this.factory = foodOfDietRequestDto.getFactory();
    this.supply = foodOfDietRequestDto.getSupply();
    this.energy = foodOfDietRequestDto.getEnergy();
    this.protein = foodOfDietRequestDto.getProtein();
    this.fat = foodOfDietRequestDto.getFat();
    this.carbohydrate = foodOfDietRequestDto.getCarbohydrate();
    this.sugar = foodOfDietRequestDto.getSugar();
  }
}
