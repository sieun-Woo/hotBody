package com.sparta.hotbody.diet.controller;

import com.sparta.hotbody.diet.dto.FoodOfDietResponseDto;
import com.sparta.hotbody.diet.dto.FoodOfDietRequestDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.service.DietService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
  public Long createDiet(
      @RequestParam("time") String time,
      @AuthenticationPrincipal UserDetails userDetails) {
    return dietService.saveDiet(userDetails, time);
  }

  // 식단에 음식 저장
  @PostMapping("/diet/food")
  public ResponseEntity<String> createFood(
      @RequestBody List<FoodOfDietRequestDto> foodOfDietRequestDtoList,
      @RequestParam("dietId") Long id) {
    return dietService.saveFood(foodOfDietRequestDtoList, id);
  }

  @GetMapping("/diet")
  public ResponseEntity readDiet(
      @RequestParam("time") String time,
      @AuthenticationPrincipal UserDetails userDetails) {
    return dietService.readDiet(time, userDetails);
  }

}
