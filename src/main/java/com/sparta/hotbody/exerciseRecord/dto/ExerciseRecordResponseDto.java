package com.sparta.hotbody.exerciseRecord.dto;


import com.sparta.hotbody.exerciseRecord.entity.ExerciseRecord;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExerciseRecordResponseDto {

  private String exercise; // 운동 명

  private int time; // 운동 시간(분)

  private int reps; // 반복 횟수

  private double calories; // 소모 열량

  private LocalDateTime createAt;
  private LocalDateTime modifiedAt;


  public ExerciseRecordResponseDto(ExerciseRecord exerciseRecord) {

    this.exercise = exerciseRecord.getExercise();
    this.time = exerciseRecord.getTime();
    this.reps = exerciseRecord.getReps();
    this.calories = exerciseRecord.calculateCalories();
    this.createAt = exerciseRecord.getCreatedAt();
    this.modifiedAt = exerciseRecord.getModifiedAt();
  }



}
