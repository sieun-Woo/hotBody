package com.sparta.hotbody.admin.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.admin.repository.AdminRepository;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.report.repository.CommentReportRepository;
import com.sparta.hotbody.report.repository.PostReportRepository;
import com.sparta.hotbody.report.repository.UserReportRepository;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import com.sparta.hotbody.user.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

  private static String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";
  @Mock
  private AdminRepository adminRepository;
  @InjectMocks
  private AdminServiceImpl adminService;
  @Spy
  private JwtUtil jwtUtil;
  @Spy
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("관리자 회원 가입 성공")
  void signup() {
    // given
    AdminSignUpRequestDto request = AdminSignUpRequestDto.builder()
        .username("nathan")
        .nickname("nathan123")
        .password("abcd1234")
        .email("nathan@gamil.com")
        .adminToken("D1d@A$5dm4&4D1d1i34n%7")
        .build();

    when(adminRepository.findByUsername(any(String.class)))
        .thenReturn(Optional.empty());

    // when
    ResponseEntity<String> response = adminService.signup(request);

    // then
    assertThat(response).isEqualTo(ResponseEntity.ok("회원가입 완료"));
  }

  @Test
  void login() {
  }

  @Test
  void logout() {
  }
}