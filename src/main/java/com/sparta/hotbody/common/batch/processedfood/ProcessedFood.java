package com.sparta.hotbody.common.batch.processedfood;

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
public class ProcessedFood {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "FOOD_ID")
  Long id;
  @Column(name = "FOOD_NAME")
  String foodName;
  @Column
  String factory;
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

  public ProcessedFood(String foodName, String factory, String oneTime, String energy,
      String protein, String fat, String car, String sugar) {
    this.foodName = foodName;
    this.factory = factory;
    this.oneTime = oneTime;
    this.energy = energy;
    this.protein = protein;
    this.fat = fat;
    this.car = car;
    this.sugar = sugar;
  }
}
