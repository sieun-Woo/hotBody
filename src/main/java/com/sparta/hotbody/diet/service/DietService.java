package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodOfDietRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface DietService {

  Page searchFood(String FoodTyp0e, String searchWord, int page);

  Long saveDiet(UserDetails userDetails, String time);

  ResponseEntity<List> readDiet(String time, UserDetails userDetails);

  ResponseEntity<String> saveFood(List<FoodOfDietRequestDto> foodOfDietRequestDto, Long id);
}
