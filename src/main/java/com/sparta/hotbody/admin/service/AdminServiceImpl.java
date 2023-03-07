package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdResponseDto;
import com.sparta.hotbody.admin.dto.FindAdminPwRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminPwResponseDto;
import com.sparta.hotbody.admin.entity.Admin;
import com.sparta.hotbody.admin.repository.AdminRepository;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.dto.UserReportResponseDto;
import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.entity.UserReportHistory;
import com.sparta.hotbody.report.repository.CommentReportRepository;
import com.sparta.hotbody.report.repository.PostReportRepository;
import com.sparta.hotbody.report.repository.UserReportRepository;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.dto.UsersResponseDto;
import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private static final String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";
  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PromoteRepository promoteRepository;
  private final AdminRepository adminRepository;
  private final UserReportRepository userReportRepository;
  private final PostReportRepository postReportRepository;
  private final CommentReportRepository commentReportRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final RefreshTokenRedisRepository refreshTokenRedisRepository;
  private final JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private String from;

  @Override
  public ResponseEntity<String> signup(AdminSignUpRequestDto adminSignUpRequestDto) {
    String username = adminSignUpRequestDto.getUsername();
    String password = passwordEncoder.encode(adminSignUpRequestDto.getPassword());

    Optional<Admin> found = adminRepository.findByUsername(username);
    if (found.isPresent()) {
      throw new CustomException(ExceptionStatus.USERNAME_IS_EXIST);
    }
    if (!adminSignUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
      throw new CustomException(ExceptionStatus.ADMIN_CODE_IS_NOT_CORRECT);
    }
    UserRole role = UserRole.ADMIN;
    Admin admin = new Admin(adminSignUpRequestDto, password, role);
    adminRepository.save(admin);
    return ResponseEntity.ok("회원가입 완료");
  }

  @Override
  public ResponseEntity<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request)
      throws UnsupportedEncodingException {
    if(!jwtUtil.validate(request)) {
      throw new CustomException(ExceptionStatus.ALREADY_LOGIN_EXCEPTION);
    }
    Admin admin = adminRepository.findByUsername(loginRequestDto.getUsername())
        .orElseThrow(() -> new CustomException(ExceptionStatus.ADMIN_IS_NOT_EXIST));
    if (!passwordEncoder.matches(loginRequestDto.getPassword(), admin.getPassword())) {
      throw new CustomException(ExceptionStatus.USERNAME_PASSWORD_DO_NOT_MATCH);
    }
    String accessToken = jwtUtil.createAccessToken(admin.getUsername(), admin.getRole());
    String refreshToken = jwtUtil.createRefreshToken(admin.getUsername(), admin.getRole());

    response.addHeader(jwtUtil.AUTHORIZATION_HEADER, accessToken);
    response.addHeader(jwtUtil.REFRESH_TOKEN, refreshToken);

    refreshTokenRedisRepository.save(new RefreshToken(refreshToken));

    return ResponseEntity.ok("로그인 완료");
  }

  @Transactional
  public ResponseEntity<String> logout(HttpServletRequest request) {
    if(jwtUtil.logout(request)) {
      return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);

    } else {
      return new ResponseEntity<>("로그인되어 있지 않습니다.", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  @Transactional
  public Page<TrainerResponseDto> getRegistrations(GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<Trainer> trainers = promoteRepository.findAll(pageable);
    return trainers.map(m -> new TrainerResponseDto(m.getUser()));
  }

  @Override
  @Transactional
  public ResponseEntity<String> permitTrainer(Long requestId) {
    Trainer trainer = promoteRepository.findById(requestId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.APPLY_IS_NOT_EXIST));
    trainer.getUser().TrainerPermission(trainer.getIntroduce());
    promoteRepository.deleteById(requestId);
    return ResponseEntity.ok("트레이너 권한을 부여하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> refuseTrainer(Long requestId) {
    promoteRepository.deleteById(requestId);
    return ResponseEntity.ok("트레이너 요청이 삭제되었습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> cancelTrainer(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.TRAINER_IS_NOT_EXIST));
    if (user.getRole().equals(UserRole.TRAINER)) {
      user.cancelPermission();
      if (promoteRepository.existsByUser(user)) {
        Trainer trainer = promoteRepository.findByUser(user);
        promoteRepository.delete(trainer);
      }
    } else {
      throw new CustomException(ExceptionStatus.NOT_TRAINER);
    }
    return ResponseEntity.ok("트레이너 권한을 삭제하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> updatePost(Long postId, PostModifyRequestDto postModifyRequestDto) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    post.modifyPost(postModifyRequestDto);
    return ResponseEntity.ok("게시글 수정을 완료하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> deletePost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    postRepository.delete(post);
    return ResponseEntity.ok("게시글 삭제가 완료되었습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST));
    comment.modifyComment(commentModifyRequestDto);
    return ResponseEntity.ok("댓글 수정을 완료하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> deleteComment(Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST));
    commentRepository.delete(comment);
    return ResponseEntity.ok("댓글 삭제가 완료되었습니다.");
  }

  @Override
  @Transactional
  public Page<UsersResponseDto> getUsers(GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<User> userPage = userRepository.findAllByRoleOrRole(UserRole.USER, UserRole.REPORTED, pageable);
    return new UsersResponseDto().toDtoPage(userPage);
  }

  @Override
  public Page<UsersResponseDto> searchUsers(String searchKeyword, GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<User> userPage = userRepository.findAllByRoleAndNicknameContainingOrRoleAndNicknameContaining(
        UserRole.USER, searchKeyword, UserRole.REPORTED, searchKeyword, pageable);
    return new UsersResponseDto().toDtoPage(userPage);
  }

  @Override
  @Transactional
  public Page<UserReportResponseDto> getReportedUsers(GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<UserReportHistory> userPage = userReportRepository.findAll(pageable);
    return userPage.map(UserReportResponseDto::new);
  }

  @Override
  public Page<UserReportResponseDto> searchReportedUsers(String searchKeyword,
      GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<UserReportHistory> userPage = userReportRepository
        .findAllByReportedNicknameContaining(searchKeyword, pageable);
    return userPage.map(UserReportResponseDto::new);
  }

  @Override
  @Transactional
  public UserProfileResponseDto getUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    if (!user.getRole().equals(UserRole.USER)) {
      throw new CustomException(ExceptionStatus.NOT_USER);
    }
    return new UserProfileResponseDto(user);
  }

  @Override
  @Transactional
  public Page<UsersResponseDto> getTrainers(GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<User> userPage = userRepository.findAllByRoleOrRole(
        UserRole.TRAINER, UserRole.REPORTED_TRAINER, pageable);
    return new UsersResponseDto().toDtoPage(userPage);
  }

  @Override
  @Transactional
  public TrainerResponseDto getTrainer(Long trainerId) {
    User user = userRepository.findById(trainerId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    if (!user.getRole().equals(UserRole.TRAINER)) {
      throw new CustomException(ExceptionStatus.NOT_TRAINER);
    }
    return new TrainerResponseDto(user);
  }

  @Override
  @Transactional
  public ResponseEntity<String> updateUserInfo(Long userId, UserProfileRequestDto userRequestDto) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    user.update(userRequestDto);
    return new ResponseEntity("사용자 정보 수정하였습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<String> makeUserSuspended(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    if (user.getRole().equals(UserRole.USER)) {
      user.reportedUserChangeRole();
    } else {
      throw new CustomException(ExceptionStatus.NOT_USER);
    }
    return ResponseEntity.ok("신고 계정으로 전환하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> makeTrainerSuspended(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    if (user.getRole().equals(UserRole.TRAINER)) {
      user.changeUserRoleReportedTrainer();
    } else {
      throw new CustomException(ExceptionStatus.NOT_TRAINER);
    }
    return ResponseEntity.ok("신고 계정으로 전환하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> makeUserNormal(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    if (user.getRole().equals(UserRole.REPORTED)) {
      user.changeUserRoleNormal();
    } else {
      throw new CustomException(ExceptionStatus.NOT_REPORTED);
    }
    return ResponseEntity.ok("정상 계정으로 전환하였습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> makeTrainerNormal(Long trainerId) {
    User trainer = userRepository.findById(trainerId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    if (trainer.getRole().equals(UserRole.REPORTED_TRAINER)) {
      trainer.changeUserRoleTrainer();
    } else {
      throw new CustomException(ExceptionStatus.NOT_REPORTED_TRAINER);
    }
    return ResponseEntity.ok("정상 트레이너 계정으로 전환하였습니다.");
  }

  // 유저 회원 탈퇴
  @Override
  @Transactional
  public ResponseEntity<String> deleteUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
    userRepository.delete(user);
    return ResponseEntity.ok("사용자 계정를 삭제하였습니다.");
  }

  // 관리자 아이디 찾기
  @Transactional
  public FindAdminIdResponseDto findAdminId(FindAdminIdRequestDto findAdminIdRequestDto)
      throws MessagingException {
    Admin admin = adminRepository.findByEmail(findAdminIdRequestDto.getEmail()).orElseThrow(
        () -> new CustomException(ExceptionStatus.EMAIL_IS_NOT_EXIST)
    );
    FindAdminIdResponseDto findAdminIdResponseDto = new FindAdminIdResponseDto(admin.getUsername());

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    mimeMessageHelper.setSubject("[hotbody] 아이디 송부");
    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(findAdminIdRequestDto.getEmail());
    mimeMessageHelper.setText("아이디: " + admin.getUsername());

    javaMailSender.send(mimeMessage);

    return findAdminIdResponseDto;
  }

  // 관리자 비밀번호 찾기
  @Transactional
  public FindAdminPwResponseDto findAdminPw(FindAdminPwRequestDto findAdminPwRequestDto)
      throws MessagingException {
    Admin admin = adminRepository.findByUsernameAndEmail(findAdminPwRequestDto.getUsername(),
        findAdminPwRequestDto.getEmail()).orElseThrow(
        () -> new CustomException(ExceptionStatus.ID_OR_EMAIL_IS_NOT_EXIST)
    );
    // 임시 비밀번호 생성
    String password = generateTempPassword();
    FindAdminPwResponseDto findAdminPwResponseDto = new FindAdminPwResponseDto(password);

    // 비밀번호 encode 후 저장
    String encodePassword = passwordEncoder.encode(password);
    admin.modifyPassword(encodePassword);

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    mimeMessageHelper.setSubject("[hotbody] 임시 비밀번호 송부");
    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(findAdminPwRequestDto.getEmail());
    mimeMessageHelper.setText("임시 비밀번호: " + password);

    javaMailSender.send(mimeMessage);

    return findAdminPwResponseDto;
  }

  // 임시 비밀번호 생성
  public String generateTempPassword() {
    char[] charSet = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    };
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.setSeed(new Date().getTime());

    StringBuffer stringBuffer= new StringBuffer();

    int index = 0;
    int length = charSet.length;

    for (int i = 0; i < 20; i++) {
      index = secureRandom.nextInt(length);
      stringBuffer.append(charSet[index]);
    }

    return stringBuffer.toString();
  }

  @Override
  public Page<PostReportResponseDto> getReportedPosts(GetPageModel getPageModel) {
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<PostReportHistory> postPage = postReportRepository.findAll(pageable);
    return postPage.map(PostReportResponseDto::new);
  }
}
