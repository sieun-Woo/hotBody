package com.sparta.hotbody.diet.service;


import com.sparta.hotbody.diet.dto.FoodResponseDto;
import com.sparta.hotbody.diet.entity.Diet;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class DietServiceImpl implements DietService {

  public FoodResponseDto searchFood(String searchFoodText, String foodType) throws IOException {
    FileInputStream file = null;
    switch (foodType) {
      case ("농산물"):
        file = new FileInputStream(
            "src/main/resources/foodData/food.xlsx"); // 농산물 DB
        break;
      case ("수산물"):
        file = new FileInputStream(
            "src/main/resources/foodData/aquaticProducts.xlsx"); // 수산물 DB
        break;
      case ("음식"):
        file = new FileInputStream(
            "src/main/resources/foodData/food.xlsx"); // 음식 DB
        break;
      case ("가공식품"):
        file = new FileInputStream(
            "src/main/resources/foodData/food.xlsx"); // 가공식품 DB
        break;
    }

    Workbook workbook = new XSSFWorkbook(file);
    Sheet sheet = workbook.getSheetAt(0);
    Cell food = null;

    for (Row row : sheet) {
      Cell cell = row.getCell(5);
      if (searchFoodText.equals(cell.getStringCellValue())) {
        food = cell;
        break;
      }
    }
    Row row = food.getRow();
    Diet diet = new Diet();
    diet.getFoodName().add(food.getStringCellValue());
    return new FoodResponseDto(row);
  }

  @Override
  public FoodResponseDto saveDiet(String searchFoodText, String foodType) {

    return null;
  }
}
