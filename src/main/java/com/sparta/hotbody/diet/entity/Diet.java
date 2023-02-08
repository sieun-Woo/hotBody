package com.sparta.hotbody.diet.entity;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Diet {

  @Id
  Long id;

  @Column
  String foodName; // 음식 이름

  @Column
  double energy; // 에너지

  @Column
  double carbohydrate; // 탄수화물

  @Column
  double protein; // 단백질

  @Column
  double fat; // 지방

}
