package com.sparta.hotbody.mypage.dto;


import com.sparta.hotbody.mypage.entity.ExerciseRecord;
import com.sparta.hotbody.mypage.service.CalorieCalculator;
import com.sparta.hotbody.mypage.service.CalorieCalculator.ExerciseType;
import java.time.LocalDateTime;


public class ExerciseRecordResponseDto {

  private String exercise; // 운동 명

  private int time; // 운동 시간(분)

  private int reps; // 반복 횟수

  private double calories; // 소모 열량


  private LocalDateTime date; // 운동 일시



  public ExerciseRecordResponseDto(ExerciseRecord exerciseRecord) {

    this.exercise = exerciseRecord.getExercise();
    this.time = exerciseRecord.getTime();
    this.reps = exerciseRecord.getReps();
    this.calories = exerciseRecord.getCalories();
    this.date = exerciseRecord.getCreatedAt();
  }


}
