package com.sparta.hotbody.mypage.service;

import com.sparta.hotbody.mypage.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.mypage.dto.ExerciseRecordResponseDto;
import com.sparta.hotbody.mypage.entity.ExerciseRecord;
import com.sparta.hotbody.mypage.repository.ExerciseRecordRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseRecordService {

  private final ExerciseRecordRepository exerciseRecordRepository;
  //운동 기록
  public List<ExerciseRecordResponseDto> getAllExercises() {
    List<ExerciseRecord> exercises = exerciseRecordRepository.findAll();
    return exercises.stream().map(ExerciseRecordResponseDto::new).collect(Collectors.toList());
  }

  public ExerciseRecordResponseDto getExerciseById(Long id) {
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  public ExerciseRecordResponseDto createExerciseRecord(ExerciseRecordRequestDto exerciseRecordRequestDto) {
    ExerciseRecord exerciseRecord = new ExerciseRecord();
    exerciseRecord.setName(exerciseRecordRequestDto.getName());
    exerciseRecord.setCaloriesBurnPerHour(exerciseRecordRequestDto.getCaloriesBurnPerHour());
    exerciseRecord = exerciseRecordRepository.save(exerciseRecord);
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  public ExerciseRecordResponseDto updateExerciseRecord(Long id, ExerciseRecordRequestDto exerciseRecordRequestDto) {
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    exerciseRecord.setName(exerciseRecordRequestDto.getName());
    exerciseRecord.setCaloriesBurnPerHour(exerciseRecordRequestDto.getCaloriesBurnPerHour());
    exerciseRecord = exerciseRecordRepository.save(exerciseRecord);
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  public void deleteExerciseRecord(Long id) {
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    exerciseRecordRepository.delete(exerciseRecord);
  }

}
