package com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts;

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
@Table(name = "AgriculturalAndLivestockProducts")
public class AgriculturalAndLivestockProducts {

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
  @Column
  String sugar;

  public AgriculturalAndLivestockProducts(Long id, String foodName, String OneTimeSupply, String energy,
      String protein, String fat, String carbohydrate, String sugar) {
    this.id = id;
    this.foodName = foodName;
    this.OneTimeSupply = OneTimeSupply;
    this.energy = energy;
    this.protein = protein;
    this.fat = fat;
    this.carbohydrate = carbohydrate;
    this.sugar = sugar;
  }
}
