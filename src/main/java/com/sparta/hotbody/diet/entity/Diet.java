package com.sparta.hotbody.diet.entity;

import com.sparta.hotbody.common.batch.food.Food;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Diet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ElementCollection
  @CollectionTable(name = "SAVED_FOOD", joinColumns = @JoinColumn(name = "DIET_ID"))
  @Column(name = "SAVED_FOOD_NAME")
  private List<String> foodName = new ArrayList<>();
  @Column
  private String energy;
  @Column
  private String protein;
  @Column
  private String fat;
  @Column
  private String sugar;

}
