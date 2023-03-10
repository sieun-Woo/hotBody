package com.sparta.hotbody.post.controller;

import com.sparta.hotbody.comment.dto.CommentResponseDto;
import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.entity.PostCategory;
import com.sparta.hotbody.post.service.PostService;

import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  // 1. 게시글 등록
  @PostMapping("/post")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN')")
  public ResponseEntity<String> createPost(
      @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.createPost(postRequestDto, userDetails);
  }

  // 2. 이미지 등록
  @PostMapping("/posts/image")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN')")
  public String createImage(
      @RequestPart MultipartFile file) {
    return postService.createImage(file);
  }

  // 2. 게시글 전체 조회
  @GetMapping("/posts/best")
  public Page<PostResponseDto> getBestPosts(GetPageModel getPageModel) {
    return postService.getBestPosts(getPageModel);
  }

  // 2-1 게시글 전체 조회
  @GetMapping("/posts")
  public Page<PostResponseDto> getAllPosts(
      @RequestParam("category") PostCategory postCategory, GetPageModel getPageModel) {
    return postService.getAllPosts(postCategory, getPageModel);
  }

  // 3. 게시글 선택 조회
  @GetMapping("/posts/{postId}")
  public PostResponseDto getPost(@PathVariable Long postId) {
    return postService.getPost(postId);
  }

  // 키워드로 게시글 검색
  @GetMapping("/posts/search")
  public Page<PostResponseDto> searchPost(
      @RequestParam(value = "category", required = false) PostCategory category,
      @RequestParam("searchType") String searchType,
      @RequestParam("searchKeyword") String searchKeyword,
      GetPageModel getPageModel) {
    return postService.searchPost(
        category, searchType, searchKeyword, getPageModel);
  }

  // 4. 게시글 수정
  @PatchMapping("/posts/{postId}")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN')")
  public void updatePost(
      @PathVariable Long postId,
      @RequestBody PostModifyRequestDto postModifyRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.updatePost(postId, postModifyRequestDto, userDetails.getUser());
  }

  // 4-1. 게시글 이미지 수정
  @PostMapping("/posts/{postId}/image")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN')")
  public String updateImage(
      @PathVariable Long postId,
      @RequestPart MultipartFile file) {
    return postService.modifyImage(postId, file);
  }

  // 5. 게시글 삭제
  @DeleteMapping("/posts/{postId}")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN')")
  public ResponseEntity<String> deletePost(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.deletePost(postId, userDetails.getUser());
  }

  // 나의 게시글 전체 조회
  @GetMapping("/my-posts")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public Page<PostResponseDto> getMyAllPosts(
      @AuthenticationPrincipal UserDetailsImpl userDetails, GetPageModel getPageModel) {
    return postService.getMyAllPosts(userDetails.getUser().getNickname(), getPageModel);
  }

  // 나의 게시글 키워드 조회
  @GetMapping("/my-posts/search")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public Page<PostResponseDto> searchMyPosts(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestParam("searchType") String searchType,
      @RequestParam("searchKeyword") String searchKeyword,
      GetPageModel getPageModel) {
    return postService.searchMyPosts(userDetails.getUser().getNickname(), searchType, searchKeyword,
        getPageModel);
  }
}