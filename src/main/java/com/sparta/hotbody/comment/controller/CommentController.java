package com.sparta.hotbody.comment.controller;

import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.comment.dto.CommentRequestDto;
import com.sparta.hotbody.comment.dto.CommentResponseDto;
import com.sparta.hotbody.comment.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/")
public class CommentController {

  private final CommentService commentService;

  // 1. 댓글 등록
  @PostMapping("/comments")
  public void createComment(@RequestBody CommentRequestDto commentRequestDto, User user) {
    commentService.createComment(commentRequestDto, user);
  }

  // 2. 댓글 전체 조회
  @GetMapping("/comments")
  public List<CommentResponseDto> getAllComments(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ) {
    return commentService.getAllComments(page - 1, size, sortBy, isAsc);
  }

  // 3. 댓글 선택 조회
  @GetMapping("/comments/{commentId}")
  public CommentResponseDto getComment(@PathVariable Long commentId) {
    return commentService.getComment(commentId);
  }

  // 4. 댓글 수정
  @PatchMapping("/comments/{commentId}")
  public void updateComment(@PathVariable Long commentId,
      @RequestBody CommentModifyRequestDto commentModifyRequestDto, User user) {
    commentService.updateComment(commentId, commentModifyRequestDto, user);
  }

  // 5. 댓글 삭제
  @DeleteMapping("/comments/{commentId}")
  public void deleteComment(@PathVariable Long commentId, User user) {
    commentService.deleteComment(commentId, user);
  }

  // 6. 댓글 좋아요
  @PostMapping("/comments/{commentId}/likes")
  public void okLikes(@PathVariable Long commentId, User user) {
    commentService.okLikes(commentId, user);
  }

  // 7. 댓글 좋아요 취소
  @DeleteMapping("/comments/{commentId}/likes")
  public void cancelLikes(@PathVariable Long commentId, User user) {
    commentService.cancelLikes(commentId, user);
  }
}
