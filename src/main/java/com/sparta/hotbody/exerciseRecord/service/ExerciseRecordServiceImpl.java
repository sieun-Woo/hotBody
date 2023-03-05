package com.sparta.hotbody.exerciseRecord.service;

import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordResponseDto;
import com.sparta.hotbody.exerciseRecord.entity.ExerciseRecord;
import com.sparta.hotbody.exerciseRecord.repository.ExerciseRecordRepository;
import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class ExerciseRecordServiceImpl implements ExerciseRecordService {

  private final ExerciseRecordRepository exerciseRecordRepository;
  private final UserRepository userRepository;


  @Override
  public Page<ExerciseRecordResponseDto> getAllExerciseRecords(int page, int size, String sortBy, boolean isAsc, UserDetailsImpl userDetails) {
    // 페이징 처리
    Long id = userDetails.getUser().getId();
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<ExerciseRecord> exerciseRecords = exerciseRecordRepository.findAllByUserId(pageable,id);

    log.info(exerciseRecords.toString());

    Page<ExerciseRecordResponseDto> exerciseRecordResponseDtos = exerciseRecords.map(e -> new ExerciseRecordResponseDto(e));

    return exerciseRecordResponseDtos;
  }

  @Override
  public ExerciseRecordResponseDto getExerciseRecordById(Long id, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.EXERCISE_RECORD_IS_NOT_EXIST));
    if (exerciseRecord.getUser().equals(user)) {
      return new ExerciseRecordResponseDto(exerciseRecord);
    } else {
      throw new CustomException(ExceptionStatus.USER_IS_NOT_EXIST);
    }
  }

  @Override
  public ExerciseRecordResponseDto createExerciseRecord(
      ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.save(
        new ExerciseRecord(user, exerciseRecordRequestDto));
    return new ExerciseRecordResponseDto(exerciseRecord);
  }

  @Override
  @Transactional
  public ExerciseRecordResponseDto updateExerciseRecord(Long id,
      ExerciseRecordRequestDto exerciseRecordRequestDto, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.EXERCISE_RECORD_IS_NOT_EXIST));
    if (exerciseRecord.getUser().getUsername().equals(user.getUsername())) {
      exerciseRecord.update(exerciseRecordRequestDto);
      return new ExerciseRecordResponseDto(
          exerciseRecordRepository.findById(exerciseRecord.getId()).get());
    } else {
      throw new CustomException(ExceptionStatus.USER_IS_NOT_EXIST);
    }

  }

  @Override
  public ResponseEntity<String> deleteExerciseRecord(Long id, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    ExerciseRecord exerciseRecord = exerciseRecordRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.EXERCISE_RECORD_IS_NOT_EXIST));
    if (exerciseRecord.getUser().equals(user)) {
      exerciseRecordRepository.deleteById(id);
      return ResponseEntity.ok("기록이 삭제되었습니다.");
    } else {
      throw new CustomException(ExceptionStatus.USER_IS_NOT_EXIST);
    }
  }
}
