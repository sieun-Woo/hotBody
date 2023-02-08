package com.sparta.hotbody.diet.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;

@Getter
public class DietResponseDto {

  private Long id;

  private List<String> foodName; // 음식 이름

  private double energy; // 에너지


  private double carbohydrate; // 탄수화물

  private double protein; // 단백질

  private double fat; // 지방

}
