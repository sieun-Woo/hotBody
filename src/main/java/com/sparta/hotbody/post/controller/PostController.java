package com.sparta.hotbody.post.controller;

import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.service.PostService;
import com.sparta.hotbody.user.entity.User;

import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/posts/")
public class PostController {

  private final PostService postService;

  // 1. 게시글 등록
  @PostMapping("")
  public void createPost(
      @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.createPost(postRequestDto, userDetails.getUser());
  }

  // 2. 게시글 전체 조회
  @GetMapping("")
  public List<PostResponseDto> getAllPosts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ) {
    return postService.getAllPosts(page - 1, size, sortBy, isAsc);
  }

  // 3. 게시글 선택 조회
  @GetMapping("{postId}")
  public PostResponseDto getPost(@PathVariable Long postId) {
    return postService.getPost(postId);
  }

  // 4. 게시글 수정
  @PatchMapping("{postId}")
  public void updatePost(
      @PathVariable Long postId,
      @RequestBody PostModifyRequestDto postModifyRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.updatePost(postId, postModifyRequestDto, userDetails.getUser());
  }

  // 5. 게시글 삭제
  @DeleteMapping("{postId}")
  public void deletePost(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.deletePost(postId, userDetails.getUser());
  }

  // 6. 게시글 좋아요
  @PostMapping("{postId}/likes")
  public void okLikes(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.okLikes(postId, userDetails.getUser());
  }

  // 7. 게시글 좋아요 취소
  @DeleteMapping("{postId}/likes")
  public void cancelLikes(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.cancelLikes(postId, userDetails.getUser());
  }
}