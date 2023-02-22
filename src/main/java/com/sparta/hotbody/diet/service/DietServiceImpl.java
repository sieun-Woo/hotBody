package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProductsRepository;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProductsRepository;
import com.sparta.hotbody.common.batch.food.FoodRepository;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFoodRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DietServiceImpl implements DietService{

  private final AgriculturalAndLivestockProductsRepository agriculturalAndLivestockProductsRepository;
  private final AquaticProductsRepository aquaticProductsRepository;
  private final FoodRepository foodRepository;
  private final ProcessedFoodRepository processedFoodRepository;

  public List searchFood(String FoodType, String searchWord) {
    switch (FoodType) {
      case ("농축산물"):
        return agriculturalAndLivestockProductsRepository.findAllByFoodNameContaining(searchWord);
      case ("수산물"):
        return aquaticProductsRepository.findAllByFoodNameContaining(searchWord);
      case ("음식"):
        return foodRepository.findAllByFoodNameContaining(searchWord);
      case ("가공음식"):
        return processedFoodRepository.findAllByFoodNameContaining(searchWord);
    }
    return null;
  }
}
