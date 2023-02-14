package com.sparta.hotbody.comment.controller;

import com.sparta.hotbody.comment.service.CommentLikeService;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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
  @PostMapping("/comments/{commentId}/likes")
  public void okLikes(
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentLikeService.okLikes(commentId, userDetails.getUser());
  }

  // 7. 댓글 좋아요 취소
  @DeleteMapping("/comments/{commentId}/likes")
  public void cancelLikes(
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentLikeService.cancelLikes(commentId, userDetails.getUser());
  }
}
