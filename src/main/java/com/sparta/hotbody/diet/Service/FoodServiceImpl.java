package com.sparta.hotbody.diet.service;

import static org.apache.poi.util.IOUtils.setByteArrayMaxOverride;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {

  @Override
  public List<FoodResponseDto> searchFood(String foodType, String searchFoodText) throws IOException {
    FileInputStream file = null;
    switch (foodType) {
      case ("농축산물"):
        file = new FileInputStream(
            "src/main/resources/foodData/agriculturalAndLivestockProducts.xlsx"); // 농산물 DB
        break;
      case ("수산물"):
        file = new FileInputStream(
            "src/main/resources/foodData/aquaticProducts.xlsx"); // 수산물 DB
        break;
      case ("음식"):
        file = new FileInputStream(
            "src/main/resources/foodData/food.xlsx"); // 음식 DB
        break;
      case ("가공음식"):
        file = new FileInputStream(
            "src/main/resources/foodData/processedFood.xlsx"); // 가공식품 DB
        break;
    }
    setByteArrayMaxOverride(230245558);
    Workbook workbook = new XSSFWorkbook(file);
    Sheet sheet = workbook.getSheetAt(0);
    Cell food = null;
    List<FoodResponseDto> foodResponseDtoList = new ArrayList<FoodResponseDto>();

    for (Row row : sheet) {
      Cell cell = row.getCell(5);
      if ((cell.getStringCellValue().contains(searchFoodText))) {
        food = cell;
        Row foodL = food.getRow();
        foodResponseDtoList.add(new FoodResponseDto(foodL));
      }
    }

    System.out.println(food.getStringCellValue());
    return foodResponseDtoList;
  }

}
