package com.sparta.hotbody.admin.controller;

import com.sparta.hotbody.admin.service.AdminService;
import com.sparta.hotbody.common.page.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;

  // 트레이너 등록 요청 조회
  @GetMapping("/requests")
  public ResponseEntity getRegistrations(PageDto pageDto) {
    return adminService.getRegistrations(pageDto);
  }

  // 트레이너 등록 요청 허용
  @PatchMapping("/users/{userId}/permissions")
  public ResponseEntity permitTrainer(@PathVariable Long userId) {
    return adminService.permitTrainer(userId);
  }

  // 트레이너 등록 요청 거부
  @DeleteMapping ("/users/{userId}/refuse")
  public ResponseEntity refuseTrainer(@PathVariable Long userId) {
    return adminService.refuseTrainer(userId);
  }
}
