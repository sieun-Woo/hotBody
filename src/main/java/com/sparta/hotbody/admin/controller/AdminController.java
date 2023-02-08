package com.sparta.hotbody.admin.controller;

import com.sparta.hotbody.admin.service.AdminService;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
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

  // ToDo: 관리자 게시글/댓글 수정/삭제 기능 유저 기능과 동일하게 수정 예정
  @PatchMapping("/posts/{postId}")
  public ResponseEntity updatePost(@PathVariable Long postId, PostModifyRequestDto postModifyRequestDto) {  // TODO: PostRequestDto 추가
    return adminService.updatePost(postId, postModifyRequestDto);
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity deletePost(@PathVariable Long postId) {
    return adminService.deletePost(postId);
  }

  @PatchMapping("/comments/{commentId}")
  public ResponseEntity updateComment(@PathVariable Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
    return adminService.updateComment(commentId, commentModifyRequestDto);
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity deleteComment(@PathVariable Long commentId) {
    return adminService.deleteComment(commentId);
  }

  // 전체 유저 정보 조회
  @GetMapping("/userlist")  // users가 더 나을 것 같습니다.
  public ResponseEntity getUserList(PageDto pageDto) {
    return adminService.getUserList(pageDto);
  }

  // 단건 유저 정보 조회
  @GetMapping("/users/{userId}")
  public ResponseEntity getUser(@PathVariable Long userId) {
    return adminService.getUser(userId);
  }

  // 전체 트레이너 정보 조회
  @GetMapping("/trainerlist")  // trainers가 더 나을 것 같습니다.
  public ResponseEntity getTrainerList(PageDto pageDto) {
    return adminService.getTrainerList(pageDto);
  }

  // 단건 트레이너 정보 조회
  @GetMapping("/trainer/{trainerId}")
  public ResponseEntity getTrainer(@PathVariable Long trainerId) {
    return adminService.getTrainer(trainerId);
  }

  // 유저 정보 수정
  @PatchMapping("/users/{userId}")
  public ResponseEntity updateUserInfo(@PathVariable Long userId) { // TODO: UserRequestDto 추가
    return adminService.updateUserInfo(userId); // TODO: UserRequestDto 추가
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity deleteUser(@PathVariable Long userId) {
    return adminService.deleteUser(userId);
  }
}
