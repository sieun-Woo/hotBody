package com.sparta.hotbody.post.controller;

import com.sparta.hotbody.post.service.PostLikeService;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {

  private final PostLikeService postLikeService;

  // 6. 게시글 좋아요
  @PostMapping("/posts/{postId}/like")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public ResponseEntity<String> pushLike(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postLikeService.pushLike(postId, userDetails.getUser());
  }

  // 7. 게시글 좋아요 취소
  @DeleteMapping("/posts/{postId}/like")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public ResponseEntity<String> cancelLike(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postLikeService.cancelLike(postId, userDetails.getUser());
    return postLikeService.cancelLike(postId, userDetails.getUser());
  }
}
