package com.sparta.hotbody.user.controller;

import com.sparta.hotbody.common.dto.MessageResponseDto;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.UserDeleteRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import com.sparta.hotbody.user.service.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final UserRepository userRepository;

  //1.회웝가입
  @PostMapping("/sign-up")
  public MessageResponseDto signup(@RequestBody @Valid SignUpRequestDto signupRequestDto) {
    return userService.signUp(signupRequestDto);
  }

  //2.로그인
  @PostMapping("/log-in")
  public MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
    MessageResponseDto msg = userService.login(loginRequestDto);
    String token = msg.getMessage();
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    return new MessageResponseDto("로그인 되었습니다.");
  }

  //2.1 REFRESH TOKEN 재발급
//  @ApiOperation(value = "토큰 재발급", notes = "토큰을 재발급한다")
//  @PostMapping(value = "/refresh")
//  @ApiImplicitParams({
//      @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access-token", required = true, dataType = "String", paramType = "header"),
//      @ApiImplicitParam(name = "REFRESH-TOKEN", value = "refresh-token", required = true, dataType = "String", paramType = "header")
//  })
//  public SingleResult<LoginResponseDto> refreshToken(
//      @RequestHeader(value="X-AUTH-TOKEN") String token,
//      @RequestHeader(value="REFRESH-TOKEN") String refreshToken ) {
//    return responseService.handleSingleResult(signService.refreshToken(token, refreshToken));
//  }

  //3. 탈퇴
  @DeleteMapping("/auth/delete")
  public MessageResponseDto delete(@RequestBody UserDeleteRequestDto deleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
    return userService.deleteUser(deleteRequestDto, userDetails.getUser());
  }

  //4. 트레이너 요청
  @PostMapping("/auth/promote")
  @PreAuthorize("hasRole('USER')")
  public TrainerResponseDto promoteUser(@RequestBody @Valid TrainerRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
    return userService.promoteTrainer(requestDto, userDetails.getUser());
  }

  //4-1. 트레이너 승인 전 취소
  @DeleteMapping("/auth/permission")
  @PreAuthorize("hasRole('USER')")
  public String deletePermission(@AuthenticationPrincipal UserDetailsImpl userDetails){
    userService.deletePermission(userDetails.getUser());
    return "삭제 완료되었습니다.";
  }

  //5. 게시판 조회


  //6. 유저 프로필 수정
//  @PatchMapping("/auth/profile")
//  public String createProfile(UserProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails
//  ){
//    return userService.createProfile(requestDto, userDetails.getUser().getUsername());
//  }

  @PutMapping("/auth/profile")
  public String createProfile(@RequestBody UserProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails
  ){
    return userService.createProfile(requestDto, userDetails.getUser().getUsername());
  }

//  @PostMapping("/auth/profile")
//  public String createProfile(
//      @RequestPart("file") MultipartFile file,
//      @RequestPart("profile") UserProfileRequestDto requestDto,
//      @AuthenticationPrincipal UserDetailsImpl userDetails
//  ){
//    return userService.createProfile(file, requestDto, userDetails.getUser());
//  }

  //7. 유저 프로필 조회
  @Transactional
  @GetMapping ("/auth/profile")
  public UserProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("연결상태 불량입니다. 다시 조회 해주시기 바랍니다.")
    );
    return UserProfileResponseDto.from(user);
  }


}