package com.sparta.hotbody.exerciseRecord.service;

import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordResponseDto;

import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


public interface ExerciseRecordService {


  //운동 기록
  Page<ExerciseRecordResponseDto> getAllExerciseRecords(int page, int size, String sortBy, boolean isAsc, UserDetailsImpl userDetails);

  ExerciseRecordResponseDto getExerciseRecordById(Long id, UserDetailsImpl userDetails) ;

  ExerciseRecordResponseDto createExerciseRecord(ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails);

  ExerciseRecordResponseDto updateExerciseRecord(Long id, ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails);

  ResponseEntity<String> deleteExerciseRecord(Long id, UserDetailsImpl userDetails) ;

}
