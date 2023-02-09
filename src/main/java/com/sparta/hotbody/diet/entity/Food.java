package com.sparta.hotbody.diet.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Food {

  @Id
  private Long id;

  @Column
  private String foodName; // 음식 이름

  @Column
  private double energy; // 에너지

  @Column
  private double carbohydrate; // 탄수화물

  @Column
  private double protein; // 단백질

  @Column
  private double fat; // 지방

  public Food(String foodName, double energy, double carbohydrate, double protein,
      double fat) {
    this.foodName = foodName;
    this.energy = energy;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
  }

}
