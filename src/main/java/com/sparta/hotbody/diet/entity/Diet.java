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
public class Diet {

  @Id
  private Long id;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "foodNames", joinColumns = @JoinColumn(name = "DIET_ID"))
  @Column
  private List<String> foodName = new ArrayList<String>(); // 음식 이름

  @Column
  private double totalEnergy; // 에너지
  @Column
  private double totalCarbohydrate; // 탄수화물
  @Column
  private double totalProtein; // 단백질
  @Column
  private double totalFat; // 지방

  public Diet(List<String> foodName, double energy, double carbohydrate, double protein,
      double fat) {
    this.foodName = foodName;
    this.totalEnergy = energy;
    this.totalCarbohydrate = carbohydrate;
    this.totalProtein = protein;
    this.totalFat = fat;
  }
}
