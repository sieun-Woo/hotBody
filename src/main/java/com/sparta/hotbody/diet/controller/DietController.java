package com.sparta.hotbody.diet.controller;

import com.sparta.hotbody.diet.dto.FoodOfDietRequestDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.entity.Diet;
import com.sparta.hotbody.diet.repository.FoodOfDietRepository;
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
  private final FoodOfDietRepository foodOfDietRepository;

  // 음식 검색
  @GetMapping("/diet/food")
  public Page<FoodResponseDto> searchFood(
      @RequestParam("foodType") String foodType,
      @RequestParam("searchWord") String searchWord,
      @RequestParam("page") int page) {
    return dietService.searchFood(foodType, searchWord, page);
  }

  // 음식을 저장하기 위한 식단 생성
  @PostMapping("/diet")
  public Long createDiet() {
    return dietService.saveDiet();
  }

  // 식단에 음식 저장
  @PostMapping("/diet/food")
  public ResponseEntity<String> createFood(
      @RequestBody FoodOfDietRequestDto foodOfDietRequestDto,
      @RequestParam("dietId") Long id) {
    return dietService.saveFood(foodOfDietRequestDto, id);
  }


  @GetMapping("/diet")
  public Diet readDiet(
      @RequestParam("time") String time) {
    return dietService.readDiet(time);
  }

}
