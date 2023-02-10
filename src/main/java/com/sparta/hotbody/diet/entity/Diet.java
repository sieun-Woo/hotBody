package com.sparta.hotbody.diet.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostLike;
import com.sparta.hotbody.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Diet extends TimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL)
  private List<Food> foods = new ArrayList<Food>();


  public Diet(User user) {
    this.user = user;
  }

  public void addFood(Food food) {
    this.foods.add(food);
    if (food.getDiet() != this) {
      food.setDiet(this);
    }
  }

  public void removeFood(Food food) {
    this.foods.remove(food);
  }
}
