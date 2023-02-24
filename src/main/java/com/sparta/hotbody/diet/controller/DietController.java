package com.sparta.hotbody.diet.controller;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.entity.Diet;
import com.sparta.hotbody.diet.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DietController {

  private final DietService dietService;

  @GetMapping("/diet/food")
  public Page<FoodResponseDto> searchFood(
      @RequestParam("foodType") String foodType,
      @RequestParam("searchWord") String searchWord,
      @RequestParam("page") int page) {
    return dietService.searchFood(foodType, searchWord, page);
  }

  @PostMapping("/diet")
  public ResponseEntity<String> createDiet(@RequestBody Diet diet) {
    return dietService.saveDiet(diet);
  }

  @GetMapping("/diet")
  public String readDiet(@RequestParam("time") String time) {
    return dietService.readDiet(time);
  }

}
