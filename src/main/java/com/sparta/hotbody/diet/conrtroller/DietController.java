package com.sparta.hotbody.diet.conrtroller;


import com.sparta.hotbody.diet.dto.DietResponseDto;
import com.sparta.hotbody.diet.dto.FoodRequestDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.repository.DietRepository;
import com.sparta.hotbody.diet.repository.FoodRepository;
import com.sparta.hotbody.diet.service.DietService;
import com.sparta.hotbody.diet.service.FoodService;
import com.sparta.hotbody.diet.service.FoodServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api")
public class DietController {

  private final DietService dietServiceImpl;
  private final FoodService foodServiceImpl;
  private final FoodRepository foodRepository;
  private final DietRepository dietRepository;

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

  @PostMapping("/diet/food")
  public void createFood(@RequestBody FoodRequestDto foodRequestDto) {
    foodServiceImpl.createFood(foodRequestDto);
  }

  @PostMapping("/diet")
  public ResponseEntity<String> createDiet() {
    return dietServiceImpl.createDiet();
  }

  @PutMapping("/diet/{dietId}/{foodId}")
  public DietResponseDto addFood(@PathVariable Long dietId, @PathVariable Long foodId) {
    return dietServiceImpl.addFood(dietId, foodId);
  }
  @GetMapping("/diet/{dietId}")
  public DietResponseDto readFood(@PathVariable Long dietId) {
    return dietServiceImpl.readFood(dietId);
  }
}
