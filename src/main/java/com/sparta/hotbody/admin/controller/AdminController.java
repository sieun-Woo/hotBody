package com.sparta.hotbody.admin.controller;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdResponseDto;
import com.sparta.hotbody.admin.dto.FindAdminPwRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminPwResponseDto;
import com.sparta.hotbody.admin.service.AdminService;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.report.dto.CommentReportResponseDto;
import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.dto.UserReportResponseDto;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.dto.UsersResponseDto;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/sign-up")
  public ResponseEntity<String> signup(@RequestBody AdminSignUpRequestDto adminSignUpRequestDto) {
    return adminService.signup(adminSignUpRequestDto);
  }

  @PostMapping("/log-in")
  public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto,
      HttpServletResponse response, HttpServletRequest request)
      throws UnsupportedEncodingException {
    return adminService.login(loginRequestDto, response, request);
  };

  @DeleteMapping("/log-out")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    return adminService.logout(request);
  }

  // 트레이너 등록 요청 조회
  @GetMapping("/apply")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<TrainerResponseDto> getRegistrations(GetPageModel getPageModel) {
    return adminService.getRegistrations(getPageModel);
  }

  // 트레이너 등록 요청 허용
  @PutMapping("/requests/{requestId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity permitTrainer(@PathVariable Long requestId) {
    return adminService.permitTrainer(requestId);
  }

  // 트레이너 등록 요청 거부
  @DeleteMapping ("/requests/{requestId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> refuseTrainer(@PathVariable Long requestId) {
    return adminService.refuseTrainer(requestId);
  }

  // 트레이너 권한 삭제
  @PutMapping("/users/{userId}/cancel")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> cancelTrainer(@PathVariable Long userId) {
    return adminService.cancelTrainer(userId);
  }

  @PatchMapping("/posts/{postId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> updatePost(@PathVariable Long postId,
      @RequestBody PostModifyRequestDto postModifyRequestDto) {
    return adminService.updatePost(postId, postModifyRequestDto);
  }

  @DeleteMapping("/posts/{postId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deletePost(@PathVariable Long postId) {
    return adminService.deletePost(postId);
  }

  @PatchMapping("/comments/{commentId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> updateComment(@PathVariable Long commentId,
      @RequestBody CommentModifyRequestDto commentModifyRequestDto) {
    return adminService.updateComment(commentId, commentModifyRequestDto);
  }

  @DeleteMapping("/comments/{commentId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
    return adminService.deleteComment(commentId);
  }

  // 전체 유저 정보 조회
  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<UsersResponseDto> getUsers(GetPageModel getPageModel) {
    return adminService.getUsers(getPageModel);
  }

  // 유저 검색 조회
  @GetMapping("/users/search")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<UsersResponseDto> searchUsers(
      @RequestParam("searchKeyword") String searchKeyword, GetPageModel getPageModel) {
    return adminService.searchUsers(searchKeyword, getPageModel);
  }

  // 신고된 유저 정보 조회
  @GetMapping("/users/report")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<UserReportResponseDto> getReportedUsers(GetPageModel getPageModel) {
    return adminService.getReportedUsers(getPageModel);
  }

  // 신고된 유저 검색 조회
  @GetMapping("/users/report/search")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<UserReportResponseDto> searchReportedUsers(
      @RequestParam("searchKeyword") String searchKeyword, GetPageModel getPageModel) {
    return adminService.searchReportedUsers(searchKeyword, getPageModel);
  }

  // 단건 유저 정보 조회
  @GetMapping("/users/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public UserProfileResponseDto getUser(@PathVariable Long userId) {
    return adminService.getUser(userId);
  }

  // 전체 트레이너 정보 조회
  @GetMapping("/trainers")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<UsersResponseDto> getTrainers(
      GetPageModel getPageModel) {
    return adminService.getTrainers(getPageModel);
  }

  // 단건 트레이너 정보 조회
  @GetMapping("/trainers/{trainerId}")
  @PreAuthorize("hasRole('ADMIN')")
  public TrainerResponseDto getTrainer(@PathVariable Long trainerId) {
    return adminService.getTrainer(trainerId);
  }

  // 유저 정보 수정
  @PatchMapping("/users/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> updateUserInfo(@PathVariable Long userId, @RequestBody
      UserProfileRequestDto userProfileRequestDto) {
    return adminService.updateUserInfo(userId, userProfileRequestDto);
  }

  // 유저 불량 유저로 전환
  @PutMapping("/users/{userId}/suspend")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> makeUserSuspended(@PathVariable Long userId) {
    return adminService.makeUserSuspended(userId);
  }

  // 불량 트레이너로 전환
  @PutMapping("/trainers/{trainerId}/suspend")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> makeTrainerSuspended(@PathVariable Long trainerId) {
    return adminService.makeTrainerSuspended(trainerId);
  };

  // 유저 정상 유저로 전환
  @PutMapping("/users/{userId}/normalize")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> makeUserNormal(@PathVariable Long userId) {
    return adminService.makeUserNormal(userId);
  }

  // 정상 트레이너로 전환
  @PutMapping("/trainers/{trainerId}/normalize")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> makeTrainerNormal(@PathVariable Long trainerId) {
      return adminService.makeTrainerNormal(trainerId);
  }

  // 유저 회원 탈퇴
  @CacheEvict
  @DeleteMapping("/users/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
    return adminService.deleteUser(userId);
  }

  // Admin 아이디 찾기
  @PutMapping("/find-id")
  public FindAdminIdResponseDto findUserId(@RequestBody FindAdminIdRequestDto findAdminIdRequestDto)
      throws MessagingException {
    return adminService.findAdminId(findAdminIdRequestDto);
  }

  // Admin 비밀번호 찾기
  @PutMapping("/find-pw")
  public FindAdminPwResponseDto findUserPw(@RequestBody FindAdminPwRequestDto findAdminPwRequestDto)
      throws MessagingException {
    return adminService.findAdminPw(findAdminPwRequestDto);
  }

  // 신고된 게시글 조회
  @GetMapping("/posts/report")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<PostReportResponseDto> getReportedPosts(GetPageModel getPageModel) {
    return adminService.getReportedPosts(getPageModel);
  }

  // 신고된 댓글 조회
  @GetMapping("/comments/report")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<CommentReportResponseDto> getReportedComments(GetPageModel getPageModel) {
    return adminService.getReportedComments(getPageModel);
  }
}