package com.sparta.hotbody.mypage.controller;

import com.sparta.hotbody.mypage.dto.ExerciseRecordRequestDto;
import com.sparta.hotbody.mypage.dto.ExerciseRecordResponseDto;
import com.sparta.hotbody.mypage.service.ExerciseRecordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/mypage")
public class ExerciseRecordController {

  private final ExerciseRecordService exerciseRecordService;

  // 운동 기록
  @PostMapping("/records")
  public ExerciseRecordResponseDto createExerciseRecord (@RequestBody ExerciseRecordRequestDto exerciseRecordRequestDto,
      @AuthenticationPrincipal UserDetails userDetails){
    return exerciseRecordService.createExerciseRecord (exerciseRecordRequestDto,userDetails);
  }

  @GetMapping("/records")
  public List<ExerciseRecordResponseDto> getAllExerciseRecords(@AuthenticationPrincipal UserDetails userDetails){
    return exerciseRecordService.getAllExerciseRecords(userDetails);
  }

  // 운동 기록 조회
  @GetMapping("/records/{recordId}")
  public ExerciseRecordResponseDto getExerciseRecordById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
    return exerciseRecordService.getExerciseRecordById(id);
  }

  // 운동 기록 수정
  @PatchMapping("/records/{recordId}")
  public ExerciseRecordResponseDto updateExercise(@PathVariable Long id, @RequestBody ExerciseRecordRequestDto exerciseRecordRequestDto) {
    return exerciseRecordService.updateExerciseRecord(id, exerciseRecordRequestDto);
  }

  // 운동 기록 삭제
  @DeleteMapping("/records/{id}")
  public void deleteExerciseRecord(@PathVariable Long id) {
    exerciseRecordService.deleteExerciseRecord(id);
  }
}
