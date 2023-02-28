package com.sparta.hotbody.exerciseRecord.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExerciseRecordRequestDto {

  private LocalDate date;
  private String exercise; // 운동 명

  private int time; // 운동 시간(분)

  private int reps; // 반복 횟수

}
