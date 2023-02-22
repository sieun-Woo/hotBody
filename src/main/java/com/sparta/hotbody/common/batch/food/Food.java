package com.sparta.hotbody.common.batch.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;
  @Column
  String foodName;
  @Column
  String oneTime;
  @Column
  String energy;
  @Column
  String protein;
  @Column
  String fat;
  @Column
  String car;
  @Column
  String sugar;

  public Food(Long id, String foodName, String oneTime, String energy, String protein, String fat,
      String car, String sugar) {
    this.id = id;
    this.foodName = foodName;
    this.oneTime = oneTime;
    this.energy = energy;
    this.protein = protein;
    this.fat = fat;
    this.car = car;
    this.sugar = sugar;
  }
}
