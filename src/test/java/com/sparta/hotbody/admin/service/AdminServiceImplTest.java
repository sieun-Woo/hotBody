package com.sparta.hotbody.admin.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.admin.entity.Admin;
import com.sparta.hotbody.admin.repository.AdminRepository;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.report.repository.CommentReportRepository;
import com.sparta.hotbody.report.repository.PostReportRepository;
import com.sparta.hotbody.report.repository.UserReportRepository;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

  private static String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";
  @Mock
  private UserRepository userRepository;
  @Mock
  private PostRepository postRepository;
  @Mock
  private CommentRepository commentRepository;
  @Mock
  private PromoteRepository promoteRepository;
  @Mock
  private AdminRepository adminRepository;
  @Mock
  private UserReportRepository userReportRepository;
  @Mock
  private PostReportRepository postReportRepository;
  @Mock
  private CommentReportRepository commentReportRepository;
  @Mock
  private RefreshTokenRedisRepository refreshTokenRedisRepository;
  @InjectMocks
  private AdminServiceImpl adminService;
  @Mock
  private JwtUtil jwtUtil;
  @Mock
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
    assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok("회원가입 완료").getStatusCode());
    assertThat(response.getBody()).isEqualTo(ResponseEntity.ok("회원가입 완료").getBody());
  }

  @Test
  @DisplayName("로그인 성공")
  void login() throws UnsupportedEncodingException {
    // given
    MockHttpServletResponse servletResponse = new MockHttpServletResponse();
    MockHttpServletRequest servletRequest = new MockHttpServletRequest();

    LoginRequestDto request = LoginRequestDto.builder()
        .username("hotbody")
        .password("abcd1234")
        .build();

    Admin admin = new Admin("hotbody", "abcd1234", UserRole.ADMIN, "hotbodyAdmin", "hotbody@gmail.com");

    when(adminRepository.findByUsername(any(String.class)))
        .thenReturn(Optional.of(admin));

    // when
    ResponseEntity<String> response = adminService.login(request, servletResponse, servletRequest);

    // then
    assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok("로그인 완료").getStatusCode());
    assertThat(response.getBody()).isEqualTo(ResponseEntity.ok("로그인 완료").getBody());

    verify(adminRepository, times(1)).saveAndFlush(any(Admin.class));
  }

  @Test
  @DisplayName("로그아웃 성공")
  void logout() {
    // given
    MockHttpServletRequest servletRequest = new MockHttpServletRequest();

    // when
    ResponseEntity<String> response = adminService.logout(servletRequest);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo("로그인되어 있지 않습니다.");
  }


  @Test
  @DisplayName("로그아웃 성공")
  void getRegistrations() {
    // given
    GetPageModel getPageModel = GetPageModel.builder()
        .page(1)
        .size(10)
        .sortBy("id")
        .isAsc(false)
        .build();



    // when
    Page<TrainerResponseDto> response = adminService.getRegistrations(getPageModel);

    // then

  }

  @Test
  void permitTrainer() {
  }

  @Test
  void refuseTrainer() {
  }

  @Test
  void cancelTrainer() {
  }

  @Test
  void updatePost() {
  }

  @Test
  void deletePost() {
  }

  @Test
  void updateComment() {
  }

  @Test
  void deleteComment() {
  }

  @Test
  void getUsers() {
  }

  @Test
  void searchUsers() {
  }

  @Test
  void getReportedUsers() {
  }

  @Test
  void searchReportedUsers() {
  }

  @Test
  void getUser() {
  }

  @Test
  void getTrainers() {
  }

  @Test
  void getTrainer() {
  }

  @Test
  void updateUserInfo() {
  }

  @Test
  void makeUserSuspended() {
  }

  @Test
  void makeTrainerSuspended() {
  }

  @Test
  void makeUserNormal() {
  }

  @Test
  void makeTrainerNormal() {
  }

  @Test
  void deleteUser() {
  }

  @Test
  void findAdminId() {
  }

  @Test
  void findAdminPw() {
  }

  @Test
  void generateTempPassword() {
  }

  @Test
  void getReportedPosts() {
  }

  @Test
  void getReportedComments() {
  }
}