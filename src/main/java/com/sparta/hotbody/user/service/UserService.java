package com.sparta.hotbody.user.service;

import com.sparta.hotbody.common.dto.MessageResponseDto;
import com.sparta.hotbody.common.jwt.dto.TokenDto;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRepository;
import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.service.UploadService;
import com.sparta.hotbody.user.dto.FindUserIdRequestDto;
import com.sparta.hotbody.user.dto.FindUserIdResponseDto;
import com.sparta.hotbody.user.dto.FindUserPwRequestDto;
import com.sparta.hotbody.user.dto.FindUserPwResponseDto;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.TrainerRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.UserDeleteRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import io.jsonwebtoken.security.SecurityException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

  private static final String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";

  // 회원가입 로직
  private final UserRepository userRepository;
  private final PromoteRepository promoteRepository;
  private final RefreshTokenRepository refreshTokenRepository; // 리프레쉬 토큰을 서버에 저장하기 위한 저장소
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final UploadService uploadService;

  @Transactional
  public MessageResponseDto signUp(SignUpRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    Optional<User> found = userRepository.findByUsername(username);
    if (found.isPresent()) {
      throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }
    UserRole role = UserRole.USER;
    if (requestDto.isAdmin()) {
      if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
        throw new SecurityException("관리자 암호가 틀렸습니다.");
      }
      role = UserRole.ADMIN;
    }
    User user = new User(requestDto, password, role);
    userRepository.save(user);
    return new MessageResponseDto("회원가입 성공");
  }


  //2.로그인
  @Transactional
  public ResponseEntity<String> login(LoginRequestDto requestDto, HttpServletResponse response)
      throws UnsupportedEncodingException {
    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new SecurityException("존재하지 않는 아이디입니다.")
    );
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new SecurityException("틀린 비밀번호입니다.");
    }

    String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
    String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());

    String encodedAccessToken = urlEncoder(accessToken);
    String encodedRefreshToken = urlEncoder(refreshToken);

    Cookie cookieAccessToken = new Cookie(jwtUtil.AUTHORIZATION_HEADER, encodedAccessToken);
    Cookie cookieRefreshToken = new Cookie(jwtUtil.REFRESH_TOKEN, encodedRefreshToken);

    response.addCookie(cookieAccessToken);
    response.addCookie(cookieRefreshToken);

    refreshTokenRepository.save(
        new RefreshToken(refreshToken.substring(7), user)); // 리프레쉬 토큰 저장소에 리프레쉬 토큰을 저장

    return new ResponseEntity("로그인 완료", HttpStatus.OK);
  }

  //3.회원탈퇴
  @Transactional
  public MessageResponseDto deleteUser(UserDeleteRequestDto deleteRequestDto, User user) {

    if (user.getRole().equals(UserRole.ADMIN) ||
        user.getUsername().equals(deleteRequestDto.getUsername()) &&
            passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {

      userRepository.deleteByUsername(deleteRequestDto.getUsername());
      return new MessageResponseDto("삭제 성공");
    }
    throw new SecurityException("가입한 회원만이 탈퇴할 수 있습니다.");
  }

  //4. 게시판 조회


  //5. 트레이너 폼 요청
  @Transactional
  public TrainerResponseDto promoteTrainer(TrainerRequestDto requestDto, User user) {
    if (promoteRepository.findByUserUsername(user.getUsername()).isPresent()) {
      throw new SecurityException("이미 트레이너 전환 요청을 하였습니다.");
    }
    Trainer trainer = new Trainer(requestDto, user);
    promoteRepository.save(trainer);
    return new TrainerResponseDto(trainer);
  }


  //6. 트레이너 폼 취소
  @Transactional
  public void deletePermission(User user) {
    User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("유저가 없습니다.")
    );

    Trainer trainer = promoteRepository.findByUserUsername(user1.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("트레이너 전환 요청을 하지 않았습니다.")
    );
    promoteRepository.deleteByUserUsername(trainer.getUser().getUsername());
  }

  //7. 유저 프로필 생성
  @Transactional
  public String createProfile(UserProfileRequestDto requestDto, UserDetails userDetails,
      MultipartFile file)
      throws IOException {
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("고객님의 개인 정보가 일치하지 않습니다.")
    );
    if (file != null) {
      Image image = uploadService.storeFile(file);
      String resourcePath = image.getResourcePath();
      user.update(requestDto, resourcePath);
      return "수정이 완료되었습니다.";
    }
    user.update(requestDto);
    userRepository.save(user);
    return "수정이 완료되었습니다.";
  }

  //8.유저 프로필 조회
  @Transactional
  public UserProfileResponseDto getUserProfile(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new IllegalArgumentException("연결상태 불량입니다 다시 유저 조회해주시기 바랍니다.")
    );
    return UserProfileResponseDto.from(user);
  }

  // 유저 아이디 찾기
  @Transactional
  public FindUserIdResponseDto findUserId(FindUserIdRequestDto findUserIdRequestDto) {
    User user = userRepository.findByEmail(findUserIdRequestDto.getEmail()).orElseThrow(
        () -> new IllegalArgumentException("입력하신 이메일을 확인해 주세요.")
    );
    FindUserIdResponseDto findUserIdResponseDto = new FindUserIdResponseDto(user.getUsername());
    return findUserIdResponseDto;
  }

  // 유저 비밀번호 찾기
  @Transactional
  public FindUserPwResponseDto findUserPw(FindUserPwRequestDto findUserPwRequestDto) {
    User user = userRepository.findByUsernameAndEmail(findUserPwRequestDto.getUsername(),
        findUserPwRequestDto.getEmail()).orElseThrow(
        () -> new IllegalArgumentException("입력하신 아이디와 이메일을 확인해 주세요.")
    );
    // 임시 비밀번호 생성
    String password = generateTempPassword();
    FindUserPwResponseDto findUserPwResponseDto = new FindUserPwResponseDto(password);

    // 비밀번호 encode 후 저장
    String encodePassword = passwordEncoder.encode(password);
    user.modifyPassword(encodePassword);
    userRepository.save(user);

    return findUserPwResponseDto;
  }

  // 쿠키에 저장하기 위한 인코더
  public String urlEncoder(String token) throws UnsupportedEncodingException {
    return URLEncoder.encode(token, "utf-8");
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
