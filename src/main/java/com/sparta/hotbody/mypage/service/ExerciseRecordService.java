package com.sparta.hotbody.mypage.service;

import com.sparta.hotbody.mypage.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.mypage.dto.ExerciseRecordResponseDto;

import java.util.List;


public interface ExerciseRecordService {


  //운동 기록
  public List<ExerciseRecordResponseDto> getAllExerciseRecords(UserDetails userDetails);

  public ExerciseRecordResponseDto getExerciseRecordById(Long id) ;

  public ExerciseRecordResponseDto createExerciseRecord(ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetails userDetails);

  public ExerciseRecordResponseDto updateExerciseRecord(Long id, ExerciseRecordRequestDto exerciseRecordRequestDto);

  public void deleteExerciseRecord(Long id) ;

}
