package com.sparta.hotbody.mypage.service;

import com.sparta.hotbody.mypage.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.mypage.dto.ExerciseRecordResponseDto;
import com.sparta.hotbody.mypage.entity.ExerciseRecord;
import com.sparta.hotbody.mypage.repository.ExerciseRecordRepository;
import com.sparta.hotbody.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ExerciseRecordServiceImpl implements ExerciseRecordService {

  private final ExerciseRecordRepository exerciseRecordRepository;
  //운동 기록
  @Override
  public List<ExerciseRecordResponseDto> getAllExerciseRecords(UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    List<ExerciseRecord> exercises = exerciseRecordRepository.findAll();
    return exercises.stream().map(ExerciseRecordResponseDto::new).collect(Collectors.toList());
  }

  @Override
  public ExerciseRecordResponseDto getExerciseRecordById(Long id) {
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  @Override
  public ExerciseRecordResponseDto createExerciseRecord(
      ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();

    ExerciseRecord exerciseRecord = new ExerciseRecord();
    exerciseRecord = exerciseRecordRepository.save(exerciseRecord);
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  @Override
  public ExerciseRecordResponseDto updateExerciseRecord(Long id, ExerciseRecordRequestDto exerciseRecordRequestDto) {
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    exerciseRecord.update(exerciseRecordRequestDto);
    exerciseRecord = exerciseRecordRepository.save(exerciseRecord);
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  @Override
  public void deleteExerciseRecord(Long id) {
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    exerciseRecordRepository.delete(exerciseRecord);
  }
}
