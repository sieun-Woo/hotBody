package com.sparta.hotbody.diet.entity;

import com.sparta.hotbody.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Diet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private String time;

  @Column(nullable = false)
  private Long userId;

  @OneToMany (mappedBy = "diet")
  private List<FoodOfDiet> foodOfDiets = new ArrayList<>();

  public Diet(Long userId, String time) {
    this.userId = userId;
    this.time = time;
  }
}
