package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.IOException;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;

public interface FoodService {
  public List<FoodResponseDto> searchFood(String text, String foodType) throws Exception;


}
