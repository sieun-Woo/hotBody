package com.sparta.hotbody.comment.controller;

import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.comment.dto.CommentRequestDto;
import com.sparta.hotbody.comment.dto.CommentResponseDto;
import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.comment.service.CommentService;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import javax.swing.Spring;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
public class CommentController {

  private final CommentService commentService;

  // 1. 댓글 등록
  @PostMapping("/posts/{postId}/comment")
  public ResponseEntity<String> createComment(
      @RequestBody CommentRequestDto commentRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long postId) {

    return commentService.createComment(userDetails.getUser(), commentRequestDto, postId);
  }

  // 2. 댓글 전체 조회
  @GetMapping("/comments")
  public Page<CommentResponseDto> getAllComments(
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
  public ResponseEntity<String> updateComment(
      @PathVariable Long commentId,
      @RequestBody CommentModifyRequestDto commentModifyRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return commentService.updateComment(commentId, commentModifyRequestDto, userDetails.getUser());
  }

  // 5. 댓글 삭제
  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<String> deleteComment(
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return commentService.deleteComment(commentId, userDetails.getUser());
  }

  // 6. 해당 게시글 댓글 전체 조회
  @GetMapping("/posts/{postId}/comments")
  public Page<CommentResponseDto> getPostComments(
      @PathVariable Long postId,
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ) {
    return commentService.getPostComments(postId, page - 1, size, sortBy, isAsc);
  }
}
