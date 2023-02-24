package com.sparta.hotbody.diet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Diet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "diet_id")
  Long id;

  @Lob
  @Column()
  String dietFood;

  @Column
  String time;

}
