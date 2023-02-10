package com.sparta.hotbody.diet.conrtroller;


import com.sparta.hotbody.diet.dto.DietResponseDto;
import com.sparta.hotbody.diet.dto.FoodRequestDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.service.DietService;
import com.sparta.hotbody.diet.service.FoodService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/auth/mypage")
public class DietController {

  private final DietService dietServiceImpl;
  private final FoodService foodServiceImpl;


  // searchFood - 식품영양성분DB로부터 식품을 조회합니다.
  @GetMapping("/diet/food")
  public List<FoodResponseDto> searchFood(@RequestParam String foodType, @RequestParam String searchFoodText) {
    try {
      List<FoodResponseDto> foodResponseDtoList = foodServiceImpl.searchFood(foodType, searchFoodText);
      return foodResponseDtoList;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }

  // createFood - 조회한 식품의 정보를 식단에 추가하기 위해 식품을 생성합니다.
  @PostMapping("/diet/food")
  public void createFood(@RequestBody FoodRequestDto foodRequestDto) {
    foodServiceImpl.createFood(foodRequestDto);
  }

//  createDiet - 식품을 관리하기 위한 식단을 생성합니다.
  @PostMapping("/diet")
  public ResponseEntity<String> createDiet(@AuthenticationPrincipal UserDetails userDetails) {
    return dietServiceImpl.createDiet(userDetails);
  }

//  readDiet - 식단을 조회합니다.
  @GetMapping("/diet/{dietId}")
  public DietResponseDto readDiet(@PathVariable Long dietId, @AuthenticationPrincipal UserDetails userDetails) {
    return dietServiceImpl.readFood(dietId, userDetails);
  }

//  addFoodInToDiet - 식단 목록에 식품을 추가합니다.
  @PutMapping("/diet/{dietId}/{foodId}")
  public DietResponseDto addFoodInToDiet(@PathVariable Long dietId, @PathVariable Long foodId) {
    return dietServiceImpl.addFood(dietId, foodId);
  }


//  removeDiet - 식단 목록에서 식품을 제거합니다.
  @DeleteMapping("/diet/{dietId}/{foodId}")
  public DietResponseDto removeFoodFromDiet(@PathVariable Long dietId, @PathVariable Long foodId) {
    return dietServiceImpl.removeFood(dietId, foodId);
  }
}
