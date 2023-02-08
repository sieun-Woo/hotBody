package com.sparta.hotbody.mypage.dto;

import lombok.Getter;

@Getter
public class ExerciseRecordRequestDto {

  private String exercise; // 운동 명

  private int time; // 운동 시간(분)

  private int reps; // 반복 횟수
}
