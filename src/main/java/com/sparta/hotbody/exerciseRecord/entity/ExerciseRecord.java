package com.sparta.hotbody.exerciseRecord.entity;

import com.sparta.hotbody.common.timestamp.TimeStamp;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.exerciseRecord.service.CalorieCalculator;
import com.sparta.hotbody.exerciseRecord.service.CalorieCalculator.ExerciseType;
import com.sparta.hotbody.user.entity.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "record")
@Getter
@NoArgsConstructor
public class ExerciseRecord extends TimeStamp {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "User_ID")
  User user;

  @Column
  private String exercise; // 운동 명
  @Column
  private int time; // 운동 시간(분)
  @Column
  private int reps; // 반복 횟수
  @Column
  private double calories; // 소모 열량

  @Column
  private LocalDate date; // 운동 날짜


  public ExerciseRecord(User user, ExerciseRecordRequestDto exerciseRecordRequestDto) {

    this.user = user;
    this.exercise = exerciseRecordRequestDto.getExercise();
    this.time = exerciseRecordRequestDto.getTime();
    this.reps = exerciseRecordRequestDto.getReps();
    this.calories = calculateCalories();
    this.date = exerciseRecordRequestDto.getDate();
  }

  public void update(ExerciseRecordRequestDto exerciseRecordRequestDto){
    this.date = exerciseRecordRequestDto.getDate();
    this.exercise = exerciseRecordRequestDto.getExercise();
    this.time = exerciseRecordRequestDto.getTime();
    this.reps = exerciseRecordRequestDto.getReps();
    this.calories = calculateCalories();
  }

  public double calculateCalories(){
    CalorieCalculator calorieCalculator = new CalorieCalculator();
    return calorieCalculator.calculateCaloriesBurned(user.getWeight(), time,
        ExerciseType.valueOf(exercise), reps);
  }


}
