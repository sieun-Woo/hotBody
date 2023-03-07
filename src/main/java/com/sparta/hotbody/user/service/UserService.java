package com.sparta.hotbody.user.service;

import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.common.dto.MessageResponseDto;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.repository.ImageRepository;
import com.sparta.hotbody.upload.service.UploadService;
import com.sparta.hotbody.user.dto.FindUserIdRequestDto;
import com.sparta.hotbody.user.dto.FindUserIdResponseDto;
import com.sparta.hotbody.user.dto.FindUserPwRequestDto;
import com.sparta.hotbody.user.dto.FindUserPwResponseDto;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import com.sparta.hotbody.user.dto.UserDeleteRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.dto.UsersResponseDto;
import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import io.jsonwebtoken.security.SecurityException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private static final String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";
  // 회원가입 로직
  private final UserRepository userRepository;
  private final PromoteRepository promoteRepository;
  private final RefreshTokenRedisRepository refreshTokenRedisRepository; // 리프레쉬 토큰을 서버에 저장하기 위한 저장소
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final UploadService uploadService;
  private final ImageRepository imageRepository;
  private final JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private String from;

  //1. 회원가입
  public ResponseEntity<String> signUp(SignUpRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    Optional<User> found = userRepository.findByUsername(username);
    if (found.isPresent()) {
      throw new CustomException(ExceptionStatus.USER_IS_NOT_EXIST);
    }
    UserRole role = UserRole.USER;
    if (requestDto.isAdmin()) {
      if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
        throw new CustomException(ExceptionStatus.ADMIN_CODE_IS_NOT_CORRECT);
      }
      role = UserRole.ADMIN;
    }
    User user = new User(requestDto, password, role);
    userRepository.save(user);
    return ResponseEntity.ok("회원가입을 완료하였습니다.");
  }


  //2. 로그인
  public ResponseEntity<String> login(LoginRequestDto requestDto, HttpServletResponse response,
      HttpServletRequest request)
      throws UnsupportedEncodingException {

    if (!jwtUtil.validate(request)) {
      throw new CustomException(ExceptionStatus.ALREADY_LOGIN_EXCEPTION);
    }

    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST)
    );
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new CustomException(ExceptionStatus.USERNAME_PASSWORD_DO_NOT_MATCH);
    }

    String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
    String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());

    response.addHeader(jwtUtil.REFRESH_TOKEN, refreshToken);
    response.addHeader(jwtUtil.AUTHORIZATION_HEADER, accessToken);

    refreshTokenRedisRepository.save(
        new RefreshToken(refreshToken)); // 리프레쉬 토큰 저장소에 리프레쉬 토큰을 저장

    return ResponseEntity.ok("로그인 완료");
  }

  //2-1. 로그아웃
  @Transactional
  public ResponseEntity<String> logout(HttpServletRequest request) {
    if (jwtUtil.logout(request)) {
      return ResponseEntity.ok("로그아웃 성공");
    } else {
      throw new CustomException(ExceptionStatus.ALREADY_LOGOUT);
    }
  }

  //3. 회원탈퇴
  public ResponseEntity<String> deleteUser(UserDeleteRequestDto deleteRequestDto, User user) {

    if (user.getRole().equals(UserRole.ADMIN) ||
      passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {

      userRepository.deleteByUsername(user.getUsername());
      return ResponseEntity.ok("회원탈퇴를 완료하였습니다.");
    }
    throw new CustomException(ExceptionStatus.PASSWORD_DO_NOT_MATCH);
  }

  //4. 트레이너 폼 요청
  public ResponseEntity<String> promoteTrainer(TrainerRequestDto requestDto, User user) {
    if (promoteRepository.findByUserUsername(user.getUsername()).isPresent()) {
      throw new CustomException(ExceptionStatus.USER_IS_NOT_EXIST);
    }
    Trainer trainer = new Trainer(requestDto, user);
    promoteRepository.save(trainer);
    return ResponseEntity.ok("트레이너 신청을 완료하였습니다.");
  }

  //5. 트레이너 폼 취소
  @Transactional
  public ResponseEntity<String> deletePermission(User user) {
    User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST)
    );

    Trainer trainer = promoteRepository.findByUserUsername(user1.getUsername()).orElseThrow(
        () -> new CustomException(ExceptionStatus.APPLY_IS_NOT_EXIST)
    );
    promoteRepository.deleteByUserUsername(trainer.getUser().getUsername());
    return ResponseEntity.ok("트레이너 신청을 취소하였습니다.");
  }

  //6. 유저 프로필 수정
  @Transactional
  public ResponseEntity<String> createProfile(UserProfileRequestDto requestDto,
      UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST)
    );
    user.update(requestDto);

    return ResponseEntity.ok("프로필을 수정하였습니다.");
  }

  //7. 유저 프로필 업로드
  @Transactional
  public Image uploadImage(MultipartFile file, UserDetails userDetails) throws IOException {

    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST)
    );
    if (user.getImage() != null) {
      Image image = imageRepository.findByResourcePath(user.getImage()).get();
      uploadService.remove(image.getResourcePath());
    }
    Image image = uploadService.storeFile(file);
    String resourcePath = image.getResourcePath();
    user.updateImage(resourcePath);

    return image;
  }

  //8. 이미지 불러오기
  public String viewImage(UserDetailsImpl userDetails) {
    Optional<String> image = Optional.of(userDetails.getUser().getImage());
    if(image.isPresent()){
      return image.get();
    } else {
      return null;
    }
  }

  //9. 유저 프로필 조회
  public UserProfileResponseDto getUserProfile(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST)
    );
    return UserProfileResponseDto.from(user);
  }

  //10. 유저 아이디 찾기
  public FindUserIdResponseDto findUserId(FindUserIdRequestDto findUserIdRequestDto)
      throws MessagingException {
    User user = userRepository.findByEmail(findUserIdRequestDto.getEmail()).orElseThrow(
        () -> new CustomException(ExceptionStatus.EMAIL_IS_NOT_EXIST)
    );
    FindUserIdResponseDto findUserIdResponseDto = new FindUserIdResponseDto(user.getUsername());

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    mimeMessageHelper.setSubject("[hotbody] 아이디 송부");
    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(findUserIdRequestDto.getEmail());
    mimeMessageHelper.setText("아이디: " + user.getUsername());

    javaMailSender.send(mimeMessage);

    return findUserIdResponseDto;
  }

  //11. 유저 비밀번호 찾기
  public FindUserPwResponseDto findUserPw(FindUserPwRequestDto findUserPwRequestDto)
      throws MessagingException {
    User user = userRepository.findByUsernameAndEmail(findUserPwRequestDto.getUsername(),
        findUserPwRequestDto.getEmail()).orElseThrow(
        () -> new CustomException(ExceptionStatus.ID_OR_EMAIL_IS_NOT_EXIST)
    );
    if (user.getUsername().equals(findUserPwRequestDto.getUsername())) {
      // 임시 비밀번호 생성
      String password = generateTempPassword();
      FindUserPwResponseDto findUserPwResponseDto = new FindUserPwResponseDto(password);
      // 비밀번호 encode 후 저장
      String encodePassword = passwordEncoder.encode(password);
      user.modifyPassword(encodePassword);
      userRepository.save(user);

      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      mimeMessageHelper.setSubject("[hotbody] 임시 비밀번호 송부");
      mimeMessageHelper.setFrom(from);
      mimeMessageHelper.setTo(findUserPwRequestDto.getEmail());
      mimeMessageHelper.setText("임시 비밀번호: " + password);

      javaMailSender.send(mimeMessage);

      return findUserPwResponseDto;
    }
    return null;
  }

  //12. 임시 비밀번호 생성
  public String generateTempPassword() {
    char[] charSet = new char[]{
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

    StringBuffer stringBuffer = new StringBuffer();

    int index = 0;
    int length = charSet.length;

    for (int i = 0; i < 20; i++) {
      index = secureRandom.nextInt(length);
      stringBuffer.append(charSet[index]);
    }

    return stringBuffer.toString();
  }

  //13. 트레이너 전체 조회
  public Page<UsersResponseDto> getTrainerList(GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<User> users = userRepository.findAllByRole(UserRole.TRAINER, pageable);
    Page<UsersResponseDto> usersResponseDto = users.map(p -> new UsersResponseDto(p));

    return usersResponseDto;
  }

  //14. 트레이너 개인 조회
  public UsersResponseDto getTrainer(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST)
    );

    if(user.getRole().equals(UserRole.TRAINER)){
      UsersResponseDto usersResponseDto = new UsersResponseDto(user);
      return usersResponseDto;
    }

    return null;
  }

}
