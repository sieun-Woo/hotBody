package com.sparta.hotbody.exerciseRecord.service;

import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordResponseDto;
import com.sparta.hotbody.exerciseRecord.entity.ExerciseRecord;
import com.sparta.hotbody.exerciseRecord.repository.ExerciseRecordRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ExerciseRecordServiceImpl implements ExerciseRecordService {

  private final ExerciseRecordRepository exerciseRecordRepository;
  private final UserRepository userRepository;

  //운동 기록
  @Override
  @Transactional
  public List<ExerciseRecordResponseDto> getAllExerciseRecords(UserDetailsImpl userDetails) {
    //User user = userRepository.findByUsername(userDetails.getUsername()).get();

    List<ExerciseRecord> exercises = exerciseRecordRepository.findAll();
    return exercises.stream().map(ExerciseRecordResponseDto::new).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ExerciseRecordResponseDto getExerciseRecordById(Long id, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("운동 기록이 없습니다."));
    if (exerciseRecord.getUser().equals(user)) {
      return new ExerciseRecordResponseDto(exerciseRecord);
    } else {
      throw new IllegalArgumentException("유저가 없습니다.");
    }

  }

  @Override
  @Transactional
  public ExerciseRecordResponseDto createExerciseRecord(
      ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.saveAndFlush(
        new ExerciseRecord(user, exerciseRecordRequestDto));
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  @Override
  @Transactional
  public ExerciseRecordResponseDto updateExerciseRecord(Long id,
      ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("운동 기록이 없습니다."));
    if (exerciseRecord.getUser().getUsername().equals(user.getUsername())) {
      exerciseRecord.update(exerciseRecordRequestDto);
      exerciseRecordRepository.flush();
      return new ExerciseRecordResponseDto(
          exerciseRecordRepository.findById(exerciseRecord.getId()).get());
    } else {
      throw new IllegalArgumentException("유저가 없습니다.");
    }

  }

  @Override
  @Transactional
  public ResponseEntity deleteExerciseRecord(Long id, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("운동 기록이 없습니다."));
    if (exerciseRecord.getUser().equals(user)) {
      exerciseRecordRepository.deleteById(id);
      return new ResponseEntity("기록이 삭제되었습니다.", HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("유저가 없습니다.");
    }
  }
}
