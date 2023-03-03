package com.sparta.hotbody.user.controller;

import com.sparta.hotbody.common.dto.MessageResponseDto;
import com.sparta.hotbody.user.dto.FindUserIdRequestDto;
import com.sparta.hotbody.user.dto.FindUserIdResponseDto;
import com.sparta.hotbody.user.dto.FindUserPwRequestDto;
import com.sparta.hotbody.user.dto.FindUserPwResponseDto;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.dto.UserDeleteRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.dto.UsersResponseDto;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import com.sparta.hotbody.user.service.UserService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  //1.회원가입
  @PostMapping("/sign-up")
  public MessageResponseDto signup(@RequestBody @Valid SignUpRequestDto signupRequestDto) {
    return userService.signUp(signupRequestDto);
  }

  //2.로그인
  @PostMapping("/log-in")
  public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto,
      HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
    return userService.login(loginRequestDto, response, request);
  }

  // 로그아웃
  @DeleteMapping("/log-out")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    return userService.logout(request);
  }


  //3. 탈퇴
  @DeleteMapping("/auth/delete")
  public MessageResponseDto delete(@RequestBody UserDeleteRequestDto deleteRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return userService.deleteUser(deleteRequestDto, userDetails.getUser());
  }

  //4. 트레이너 요청
  @PostMapping("/auth/promote")
  @PreAuthorize("hasRole('USER')")
  public void promoteUser(@RequestBody @Valid TrainerRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    userService.promoteTrainer(requestDto, userDetails.getUser());
  }

  //4-1. 트레이너 승인 전 취소
  @DeleteMapping("/auth/permission")
  @PreAuthorize("hasRole('USER')")
  public String deletePermission(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    userService.deletePermission(userDetails.getUser());
    return "삭제 완료되었습니다.";
  }

  //5. 유저 프로필 작성
  @PutMapping("/auth/profile")
  public String createProfile(
      @RequestBody UserProfileRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
    return userService.createProfile(requestDto, userDetails);
  }

  //6. 유저 프로필 사진 첨부
  @PostMapping("/auth/profile/image")
  public ResponseEntity<String> saveProfileImage(
      @RequestPart MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

    userService.uploadImage(file, userDetails);

    return new ResponseEntity<>("업로드 되었습니다.", HttpStatus.OK);
  }

  //6-1. 프로필 이미지 조회
  @GetMapping("/auth/profile/image")
  public String downloadImage(@AuthenticationPrincipal UserDetailsImpl userDetails) throws MalformedURLException {
    return userService.viewImage(userDetails);
  }

  //7. 유저 프로필 조회
  @GetMapping("/auth/profile")
  public UserProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("연결상태 불량입니다. 다시 조회 해주시기 바랍니다.")
    );
    return UserProfileResponseDto.from(user);
  }

  //8. 유저 아이디 찾기
  @PutMapping("/find-id")
  public FindUserIdResponseDto findUserId(@RequestBody FindUserIdRequestDto findUserIdRequestDto)
      throws MessagingException {
    return userService.findUserId(findUserIdRequestDto);
  }

  //9. 유저 비밀번호 찾기
  @PutMapping("/find-pw")
  public FindUserPwResponseDto findUserPw(@RequestBody FindUserPwRequestDto findUserPwRequestDto)
      throws MessagingException {
    return userService.findUserPw(findUserPwRequestDto);
  }

  //10.트레이너 전체 조회
//  @GetMapping("/auth/trainers")
//  public ResponseEntity getTrainerList(@RequestParam(value="currentPage") int pageNum) {
//    return userService.getTrainerList(pageNum);
//  }

  @GetMapping("/auth/trainers")
  public Page<UsersResponseDto> getTrainerList(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ) {
    return userService.getTrainerList(page - 1, size, sortBy, isAsc);
  }

  //11. 트레이너 조회
  @GetMapping("/auth/trainers/{trainerId}")
  public UsersResponseDto getTrainer(@PathVariable Long trainerId) {
    return userService.getTrainer(trainerId);
  }

}