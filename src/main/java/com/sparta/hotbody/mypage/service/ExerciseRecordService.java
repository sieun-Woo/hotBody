package com.sparta.hotbody.mypage.service;

import com.sparta.hotbody.mypage.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.mypage.dto.ExerciseRecordResponseDto;

import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;


public interface ExerciseRecordService {


  //운동 기록
  public List<ExerciseRecordResponseDto> getAllExerciseRecords(UserDetailsImpl userDetails);

  public ExerciseRecordResponseDto getExerciseRecordById(Long id) ;

  public ExerciseRecordResponseDto createExerciseRecord(ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails);

  public ExerciseRecordResponseDto updateExerciseRecord(Long id, ExerciseRecordRequestDto exerciseRecordRequestDto);

  public void deleteExerciseRecord(Long id) ;

}
