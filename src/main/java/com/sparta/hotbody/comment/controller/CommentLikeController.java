package com.sparta.hotbody.comment.controller;

import com.sparta.hotbody.comment.service.CommentLikeService;
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
public class CommentLikeController {

  private final CommentLikeService commentLikeService;

  // 6. 댓글 좋아요
  @PostMapping("/comments/{commentId}/like")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public ResponseEntity<String> pushLike(
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return commentLikeService.pushLike(commentId, userDetails.getUser());
  }

  // 7. 댓글 좋아요 취소
  @DeleteMapping("/comments/{commentId}/like")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public ResponseEntity<String> cancelLike(
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return commentLikeService.cancelLike(commentId, userDetails.getUser());
  }
}
