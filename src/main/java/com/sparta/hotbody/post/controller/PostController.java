package com.sparta.hotbody.post.controller;

import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.dto.PostSearchRequestDto;
import com.sparta.hotbody.post.entity.PostCategory;
import com.sparta.hotbody.post.service.PostService;

import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  // 1. 게시글 등록
  @PostMapping("/posts")
  public ResponseEntity<String> createPost(
      @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.createPost(postRequestDto, userDetails);
  }

  // 2. 이미지 등록
  @PostMapping("/posts/image")
  public String createImage(
      @RequestPart MultipartFile file) throws IOException {
    return postService.createImage(file);
  }

  // 2. 게시글 전체 조회
  @GetMapping("/posts")
  public Page<PostResponseDto> getAllPosts(
      @RequestParam("category") PostCategory postCategory,
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ) {
    return postService.getAllPosts(postCategory, page - 1, size, sortBy, isAsc);
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
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc) {
    return postService.searchPost(category, searchType, searchKeyword, page - 1, size, sortBy, isAsc);
  }

  // 4. 게시글 수정
  @PatchMapping("/posts/{postId}")
  public void updatePost(
      @PathVariable Long postId,
      @RequestBody PostModifyRequestDto postModifyRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
    postService.updatePost(postId, postModifyRequestDto, userDetails.getUser());
  }


  // 5. 게시글 이미지 수정
  @PatchMapping("/posts/image")
  public String updateImage(
      @RequestPart MultipartFile file) throws IOException {
    return postService.updateImage(file);
  }

  // 6. 게시글 삭제
  @DeleteMapping("/posts/{postId}")
  public void deletePost(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.deletePost(postId, userDetails.getUser());
  }
}