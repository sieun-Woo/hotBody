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
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
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
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final RefreshTokenRedisRepository refreshTokenRedisRepository;
  private final JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private String from;

  @Override
  public ResponseEntity signup(AdminSignUpRequestDto adminSignUpRequestDto) {
    String username = adminSignUpRequestDto.getUsername();
    String password = passwordEncoder.encode(adminSignUpRequestDto.getPassword());

    Optional<Admin> found = adminRepository.findByUsername(username);
    if (found.isPresent()) {
      throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
    }
    if (!adminSignUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
      throw new IllegalArgumentException("관리자 암호가 틀렸습니다.");
    }
    UserRole role = UserRole.ADMIN;
    Admin admin = new Admin(adminSignUpRequestDto, password, role);
    adminRepository.save(admin);
    return new ResponseEntity("회원가입 완료", HttpStatus.OK);
  }

  @Override
  public ResponseEntity login(LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request)
      throws UnsupportedEncodingException {
    if(!jwtUtil.validate(request)) {
      return new ResponseEntity<>("중복 로그인 입니다.", HttpStatus.OK);
    }
    Admin admin = adminRepository.findByUsername(loginRequestDto.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
    if (!passwordEncoder.matches(loginRequestDto.getPassword(), admin.getPassword())) {
      throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
    String accessToken = jwtUtil.createAccessToken(admin.getUsername(), admin.getRole());
    String refreshToken = jwtUtil.createRefreshToken(admin.getUsername(), admin.getRole());

    response.addHeader(jwtUtil.AUTHORIZATION_HEADER, accessToken);
    response.addHeader(jwtUtil.REFRESH_TOKEN, refreshToken);

    refreshTokenRedisRepository.save(new RefreshToken(refreshToken));

    return new ResponseEntity("로그인 완료", HttpStatus.OK);
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
  public ResponseEntity getRegistrations(int pageNum) {
    Page<Trainer> trainerList = promoteRepository.findAll(new PageDto(pageNum).toPageable());
    if (trainerList.isEmpty()) {
      throw new IllegalArgumentException("페이지가 존재하지 않습니다.");
    }
    Page<TrainerResponseDto> result = trainerList
        .map(m -> new TrainerResponseDto(m));
    return new ResponseEntity(result, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity permitTrainer(Long requestId) {
    Trainer trainer = promoteRepository.findById(requestId)
        .orElseThrow(() -> new IllegalArgumentException("요청이 존재하지 않습니다."));
    trainer.getUser().TrainerPermission(trainer.getIntroduce());
    promoteRepository.deleteById(requestId);
    return new ResponseEntity("트레이너 권한을 부여하였습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity refuseTrainer(Long requestId) {
    promoteRepository.deleteById(requestId);
    return new ResponseEntity("트레이너 요청이 삭제되었습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity cancelTrainer(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (user.getRole().equals(UserRole.TRAINER)) {
      user.cancelPermission();
      if (promoteRepository.existsByUser(user)) {
        Trainer trainer = promoteRepository.findByUser(user);
        promoteRepository.delete(trainer);
      }
    } else {
      throw new IllegalArgumentException("해당 유저는 트레이너가 아닙니다.");
    }
    return new ResponseEntity("트레이너 권한을 삭제하였습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity updatePost(Long postId, PostModifyRequestDto postModifyRequestDto) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    post.modifyPost(postModifyRequestDto);
    return new ResponseEntity("게시글 수정을 완료하였습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity deletePost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    postRepository.delete(post);
    return new ResponseEntity("게시글 삭제가 완료되었습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    comment.modifyComment(commentModifyRequestDto);
    return new ResponseEntity("댓글 수정을 완료하였습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity deleteComment(Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    commentRepository.delete(comment);
    return new ResponseEntity("댓글 삭제가 완료되었습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity getUserList(int pageNum) {
    Page<User> userPage = userRepository.findAllByRoleOrRole(UserRole.USER, UserRole.REPORTED, new PageDto(pageNum).toPageable());
    if (userPage.isEmpty()) {
      throw new IllegalArgumentException("페이지가 존재하지 않습니다.");
    }
    Page<UsersResponseDto> userResponseDtoPage = new UsersResponseDto().toDtoPage(userPage);
    return new ResponseEntity(userResponseDtoPage, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity getUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (!user.getRole().equals(UserRole.USER)) {
      throw new IllegalArgumentException("유저가 아닙니다.");
    }
    return new ResponseEntity(new UserProfileResponseDto(user), HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity getTrainerList(int pageNum) {
    Page<User> userPage = userRepository.findAllByRoleOrRole(UserRole.TRAINER, UserRole.REPORTED_TRAINER, new PageDto(pageNum).toPageable());
    if (userPage.isEmpty()) {
      throw new IllegalArgumentException("페이지가 존재하지 않습니다.");
    }
      Page<UsersResponseDto> userResponseDtoPage = new UsersResponseDto().toDtoPage(userPage);
    return new ResponseEntity(userResponseDtoPage, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity getTrainer(Long trainerId) {
    User user = userRepository.findById(trainerId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (!user.getRole().equals(UserRole.TRAINER)) {
      throw new IllegalArgumentException("트레이너가 아닙니다.");
    }
    return new ResponseEntity(user, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity updateUserInfo(Long userId, UserProfileRequestDto userRequestDto) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    user.update(userRequestDto);
    return new ResponseEntity("사용자 정보 수정하였습니다.", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity makeUserSuspended(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (user.getRole().equals(UserRole.USER)) {
      user.reportedUserChangeRole();
    } else {
      throw new IllegalArgumentException("전환에 실패하였습니다.");
    }
    return new ResponseEntity("신고 계정으로 전환하였습니다.",  HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity makeTrainerSuspended(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (user.getRole().equals(UserRole.TRAINER)) {
      user.changeUserRoleReportedTrainer();
    } else {
      throw new IllegalArgumentException("전환에 실패하였습니다.");
    }
    return new ResponseEntity("신고 계정으로 전환하였습니다.",  HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity makeUserNormal(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (user.getRole().equals(UserRole.REPORTED)) {
      user.changeUserRoleNormal();
    } else {
      throw new IllegalArgumentException("전환에 실패하였습니다.");
    }
    return new ResponseEntity("정상 계정으로 전환하였습니다.",  HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity makeTrainerNormal(Long trainerId) {
    User trainer = userRepository.findById(trainerId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    if (trainer.getRole().equals(UserRole.REPORTED_TRAINER)) {
      trainer.changeUserRoleTrainer();
    } else {
      throw new IllegalArgumentException("전환에 실패하였습니다.");
    }
    return new ResponseEntity("정상 트레이너 계정으로 전환하였습니다.",  HttpStatus.OK);
  }

  // 유저 회원 탈퇴
  @Override
  @Transactional
  public ResponseEntity deleteUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    userRepository.delete(user);
    return new ResponseEntity("사용자 계정를 삭제하였습니다.", HttpStatus.OK);
  }

  // 관리자 아이디 찾기
  @Transactional
  public FindAdminIdResponseDto findAdminId(FindAdminIdRequestDto findAdminIdRequestDto)
      throws MessagingException {
    Admin admin = adminRepository.findByEmail(findAdminIdRequestDto.getEmail()).orElseThrow(
        () -> new IllegalArgumentException("입력하신 이메일을 확인해 주세요.")
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
        () -> new IllegalArgumentException("입력하신 아이디와 이메일을 확인해 주세요.")
    );
    // 임시 비밀번호 생성
    String password = generateTempPassword();
    FindAdminPwResponseDto findAdminPwResponseDto = new FindAdminPwResponseDto(password);

    // 비밀번호 encode 후 저장
    String encodePassword = passwordEncoder.encode(password);
    admin.modifyPassword(encodePassword);
    adminRepository.save(admin);

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
}
