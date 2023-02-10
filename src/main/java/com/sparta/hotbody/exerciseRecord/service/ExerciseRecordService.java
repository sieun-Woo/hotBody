package com.sparta.hotbody.exerciseRecord.service;

import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordResponseDto;

import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import org.springframework.http.ResponseEntity;


public interface ExerciseRecordService {


  //운동 기록
  public List<ExerciseRecordResponseDto> getAllExerciseRecords(UserDetailsImpl userDetails);

  public ExerciseRecordResponseDto getExerciseRecordById(Long id, UserDetailsImpl userDetails) ;

  public ExerciseRecordResponseDto createExerciseRecord(ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails);

  public ExerciseRecordResponseDto updateExerciseRecord(Long id, ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails);

  public ResponseEntity deleteExerciseRecord(Long id, UserDetailsImpl userDetails) ;

}
