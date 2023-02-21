package com.sparta.hotbody.common.batch.aquaticProducts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AquaticProducts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;
  @Column
  String foodName;
  String oneTime;
  @Column
  String energy;
  @Column
  String protein;
  @Column
  String fat;
  @Column
  String car;

  public AquaticProducts(Long id, String foodName, String oneTime, String energy, String protein,
      String fat, String car) {
    this.id = id;
    this.foodName = foodName;
    this.oneTime = oneTime;
    this.energy = energy;
    this.protein = protein;
    this.fat = fat;
    this.car = car;
  }
}
