package com.sparta.hotbody.user.controller;

import com.sparta.hotbody.user.repository.TrainerLikeRepository;
import com.sparta.hotbody.user.service.TrainerLikeService;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/trainers")
public class TrainerLikeController {
  private final TrainerLikeService trainerLikeService;

  //1. 트레이너 좋아요
  @PostMapping("/{trainerId}")
  public void addLike(
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
      @PathVariable Long trainerId) {
    trainerLikeService.addLike(trainerId, userDetailsImpl.getUser().getId());
  }

  //2. 트레이너 좋아요 취소
  @DeleteMapping("/{trainerId}")
  public void cancelLike(
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
      @PathVariable Long trainerId) {
    trainerLikeService.cancelLike(trainerId, userDetailsImpl.getUser());
  }

}
