package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProducts;
import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProductsRepository;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProducts;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProductsRepository;
import com.sparta.hotbody.common.batch.food.Food;
import com.sparta.hotbody.common.batch.food.FoodRepository;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFood;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFoodRepository;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRepository;
import com.sparta.hotbody.diet.dto.FoodOfDietResponseDto;
import com.sparta.hotbody.diet.dto.FoodOfDietRequestDto;
import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.entity.Diet;
import com.sparta.hotbody.diet.entity.FoodOfDiet;
import com.sparta.hotbody.diet.repository.DietRepository;
import com.sparta.hotbody.diet.repository.FoodOfDietRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DietServiceImpl implements DietService {

  private final AgriculturalAndLivestockProductsRepository agriculturalAndLivestockProductsRepository;
  private final AquaticProductsRepository aquaticProductsRepository;
  private final FoodRepository foodRepository;
  private final ProcessedFoodRepository processedFoodRepository;
  private final DietRepository dietRepository;
  private final FoodOfDietRepository foodOfDietRepository;
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  public Page<FoodResponseDto> searchFood(String FoodType, String searchWord, int page) {
    Sort sort = Sort.by(Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page - 1, 10, sort);

    switch (FoodType) {
      case ("농축산물"):
        Page<AgriculturalAndLivestockProducts> agr = agriculturalAndLivestockProductsRepository.findAllByFoodNameContaining(
            searchWord, pageable);
        Page<FoodResponseDto> agrPage = agr.map(
            agriculturalAndLivestockProducts -> new FoodResponseDto(
                agriculturalAndLivestockProducts));
        return agrPage;

      case ("수산물"):
        Page<AquaticProducts> aqu = aquaticProductsRepository.findAllByFoodNameContaining(
            searchWord, pageable);
        Page<FoodResponseDto> aquPage = aqu.map(
            aquaticProducts -> new FoodResponseDto(aquaticProducts)
        );

        return aquPage;

      case ("음식"):
        Page<Food> foo = foodRepository.findAllByFoodNameContaining(searchWord,
            pageable);
        Page<FoodResponseDto> fooPage = foo.map(
            food -> new FoodResponseDto(food)
        );
        return fooPage;

      case ("가공식품"):
        Page<ProcessedFood> pro = processedFoodRepository.findAllByFoodNameContaining(
            searchWord, pageable);
        Page<FoodResponseDto> proPage = pro.map(
            processedFood -> new FoodResponseDto(processedFood)
        );
        return proPage;
    }
    return null;
  }

  @Transactional
  @Override
  public Long saveDiet(UserDetails userDetails, String time) {
    Long id = userRepository.findByUsername(userDetails.getUsername()).get().getId();
    if (dietRepository.findByUserIdAndTime(id, time).isPresent()) {
      return dietRepository.findByUserIdAndTime(id, time).get().getId();
    }
    return dietRepository.saveAndFlush(new Diet(id, time)).getId();
  }

  @Override
  public ResponseEntity readDiet(String time, UserDetails userDetails) {
    List<FoodOfDietResponseDto> foodOfDietResponseDtoList = new ArrayList<>();
    Long id = userRepository.findByUsername(userDetails.getUsername()).get().getId();
    if (dietRepository.findByUserIdAndTime(id, time).isPresent()) {
      Diet diet = dietRepository.findByUserIdAndTime(id, time).get();
      for (FoodOfDiet foodOfDiet : diet.getFoodOfDiets()) {
        foodOfDietResponseDtoList.add(new FoodOfDietResponseDto(foodOfDiet));
      }
      return new ResponseEntity<List>(foodOfDietResponseDtoList, HttpStatus.OK);
    }
    return new ResponseEntity<String>("식단이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
  }

  @Transactional
  @Override
  public ResponseEntity<String> saveFood(List<FoodOfDietRequestDto> foodOfDietRequestDtoList,
      Long id) {
    Diet diet = dietRepository.findById(id).get();
    for (FoodOfDietRequestDto foodOfDietRequestDto : foodOfDietRequestDtoList) {
      FoodOfDiet foodOfDiet = new FoodOfDiet(foodOfDietRequestDto, diet);
      foodOfDietRepository.saveAndFlush(foodOfDiet);
    }
    return new ResponseEntity<>("음식 저장 성공", HttpStatus.OK);
  }
}
