package com.sparta.hotbody.diet.service;

import java.util.List;
import org.springframework.data.domain.Page;

public interface DietService {

  public Page searchFood(String FoodTyp0e, String searchWord, int page);

}
