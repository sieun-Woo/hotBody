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
  @Column
  Long id;
  @Column
  String foodName;
  @Column
  String factory;
  @Column
  String OneTimeSupply;
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

  public ProcessedFood(String foodName, String factory, String OneTimeSupply, String energy,
      String protein, String fat, String carbohydrate, String sugar) {
    this.foodName = foodName;
    this.factory = factory;
    this.OneTimeSupply = OneTimeSupply;
    this.energy = energy;
    this.protein = protein;
    this.fat = fat;
    this.carbohydrate = carbohydrate;
    this.sugar = sugar;
  }
}
