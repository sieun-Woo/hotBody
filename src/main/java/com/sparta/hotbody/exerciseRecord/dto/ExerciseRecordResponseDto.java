package com.sparta.hotbody.exerciseRecord.dto;


import com.sparta.hotbody.exerciseRecord.entity.ExerciseRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExerciseRecordResponseDto {

  private Long id;
  private String exercise; // 운동 명

  private int time; // 운동 시간(분)

  private int reps; // 반복 횟수

  private double calories; // 소모 열량

  private LocalDate date;

  private String createAt;
  private String modifiedAt;


  public ExerciseRecordResponseDto(ExerciseRecord exerciseRecord) {
    this.id = exerciseRecord.getId();
    this.exercise = exerciseRecord.getExercise();
    this.time = exerciseRecord.getTime();
    this.reps = exerciseRecord.getReps();
    this.calories = exerciseRecord.calculateCalories();
    this.date = exerciseRecord.getDate();
    this.createAt = exerciseRecord.getCreatedAt().format(DateTimeFormatter.ISO_DATE);
    this.modifiedAt = exerciseRecord.getModifiedAt().format(DateTimeFormatter.ISO_DATE);
  }



}
