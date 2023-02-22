package com.sparta.hotbody.common.batch.aquaticProducts;

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
public class AquaticProducts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;
  @Column
  String foodName;
  String OneTimeSupply;
  @Column
  String energy;
  @Column
  String protein;
  @Column
  String fat;
  @Column
  String carbohydrate;

  public AquaticProducts(Long id, String foodName, String OneTimeSupply, String energy, String protein,
      String fat, String carbohydrate) {
    this.id = id;
    this.foodName = foodName;
    this.OneTimeSupply = OneTimeSupply;
    this.energy = energy;
    this.protein = protein;
    this.fat = fat;
    this.carbohydrate = carbohydrate;
  }
}
