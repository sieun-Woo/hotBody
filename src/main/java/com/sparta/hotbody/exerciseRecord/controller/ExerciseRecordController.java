package com.sparta.hotbody.exerciseRecord.controller;

import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.exerciseRecord.dto.ExerciseRecordResponseDto;
import com.sparta.hotbody.exerciseRecord.service.ExerciseRecordServiceImpl;
import com.sparta.hotbody.post.entity.PostCategory;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExerciseRecordController {

  private final ExerciseRecordServiceImpl exerciseRecordService;

  // 운동 기록
  @PreAuthorize("hasAnyRole('USER','ADMIN', 'TRAINER','REPORTED')")
  @PostMapping("/record")
  public ExerciseRecordResponseDto createExerciseRecord(
      @RequestBody ExerciseRecordRequestDto exerciseRecordRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return exerciseRecordService.createExerciseRecord(exerciseRecordRequestDto, userDetails);
  }

  @GetMapping("/records")
  @PreAuthorize("hasAnyRole('USER','ADMIN', 'TRAINER','REPORTED')")
  public Page<ExerciseRecordResponseDto> getAllExerciseRecords(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return exerciseRecordService.getAllExerciseRecords(page-1, size, sortBy, isAsc, userDetails);
  }

  // 운동 기록 조회
  @GetMapping("/records/{recordId}")
  @PreAuthorize("hasAnyRole('USER','ADMIN', 'TRAINER','REPORTED')")
  public ExerciseRecordResponseDto getExerciseRecordById(@PathVariable Long recordId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return exerciseRecordService.getExerciseRecordById(recordId, userDetails);
  }

  // 운동 기록 수정
  @PatchMapping("/records/{recordId}")
  @PreAuthorize("hasAnyRole('USER','ADMIN', 'TRAINER','REPORTED')")
  public ExerciseRecordResponseDto updateExercise(@PathVariable Long recordId,
      @RequestBody ExerciseRecordRequestDto exerciseRecordRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return exerciseRecordService.updateExerciseRecord(recordId, exerciseRecordRequestDto,
        userDetails);
  }

  // 운동 기록 삭제
  @DeleteMapping("/records/{recordId}")
  @PreAuthorize("hasAnyRole('USER','ADMIN', 'TRAINER','REPORTED')")
  public ResponseEntity deleteExerciseRecord(@PathVariable Long recordId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return exerciseRecordService.deleteExerciseRecord(recordId, userDetails);
  }
}
