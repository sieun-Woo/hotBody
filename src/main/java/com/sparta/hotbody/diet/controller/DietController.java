package com.sparta.hotbody.diet.controller;

import com.sparta.hotbody.diet.service.DietService;
import com.sparta.hotbody.diet.service.DietServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DietController {

  private final DietService dietService;

  @GetMapping("/diet/food")
  public List searchFood(
      @RequestParam("foodType") String foodType,
      @RequestParam("searchWord") String searchWord) {
    return dietService.searchFood(foodType, searchWord);
  }

}
