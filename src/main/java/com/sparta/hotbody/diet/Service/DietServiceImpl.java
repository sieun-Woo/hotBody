package com.sparta.hotbody.diet.service;


import com.sparta.hotbody.diet.dto.DietResponseDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.entity.Diet;
import com.sparta.hotbody.diet.entity.Food;
import com.sparta.hotbody.diet.repository.DietRepository;
import com.sparta.hotbody.diet.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DietServiceImpl implements DietService {

  private final DietRepository dietRepository;
  private final FoodRepository foodRepository;

  @Transactional
  @Override
  public ResponseEntity<String> createDiet() {
    dietRepository.save(new Diet());

    return new ResponseEntity<>("생성되었습니다.",HttpStatus.OK);
  }

  @Transactional
  @Override
  public DietResponseDto addFood(Long dietId, Long foodId) {
    Diet diet = dietRepository.findById(dietId).orElseThrow(
        () -> new IllegalArgumentException("식단이 존재하지 않습니다.")
    );
    Food food =foodRepository.findById(foodId).orElseThrow(
        () -> new IllegalArgumentException("음식이 존재하지 않습니다.")
    );
    diet.addFood(food);
    return new DietResponseDto(diet);
  }

  @Override
  public DietResponseDto readFood(Long dietId) {
    Diet diet = dietRepository.findById(dietId).orElseThrow(
        () -> new IllegalArgumentException("식단이 존재하지 않습니다.")
    );
    return new DietResponseDto(diet);
  }
}
