package com.sparta.hotbody.user.service;

import com.sparta.hotbody.common.dto.MessageResponseDto;
import com.sparta.hotbody.common.jwt.JwtUtil;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.PromoteTrainerRequestDto;
import com.sparta.hotbody.user.dto.PromoteTrainerResponseDto;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.UserDeleteRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.entity.Promote;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.PromoteRepository;
import com.sparta.hotbody.user.repository.UserRepository;
import io.jsonwebtoken.security.SecurityException;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

  //1.회원가입
//  @Transactional
//  public MessageResponseDto signUp(SignUpRequestDto requestDto) {
//    String username = requestDto.getUsername();
//    String password = passwordEncoder.encode(requestDto.getPassword());
//    String nickname = requestDto.getNickname();
//    Integer gender  = requestDto.getGender();
//    Date birthday = requestDto.getBirthday();
//
//    Optional<User> found = userRepository.findByUsername(username);
//    if (found.isPresent()) {
//      throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//    }
//    UserRole role = UserRole.USER;
//    if (requestDto.isAdmin()) {
//      if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//        throw new SecurityException("관리자 암호가 틀렸습니다.");
//      }
//      role = UserRole.ADMIN;
//    }
//    User user = new User(username, password, role, nickname, gender, birthday);
//    userRepository.save(user);
//    return new MessageResponseDto("회원가입 성공");
//  }

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
  public MessageResponseDto login(LoginRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new SecurityException("사용자를 찾을수 없습니다.")
    );
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new SecurityException("사용자를 찾을수 없습니다.");
    }

    return new MessageResponseDto(jwtUtil.createToken(user.getUsername(), user.getRole()));
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
  public PromoteTrainerResponseDto promoteTrainer(PromoteTrainerRequestDto requestDto, User user) {
    if (promoteRepository.findByUserUsername(user.getUsername()).isPresent()) {
      throw new SecurityException("이미 판매자 전환 요청을 하였습니다.");
    }
    Promote promote = new Promote(requestDto, user);
    promoteRepository.save(promote);
    return new PromoteTrainerResponseDto(promote);
  }


  //6. 트레이너 폼 취소
  @Transactional
  public void deletePermission(User user) {
    User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("유저가 없습니다.")
    );

    Promote promote = promoteRepository.findByUserUsername(user1.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("판매자 요청을 하지 않았습니다.")
    );
    promoteRepository.deleteByUserUsername(promote.getUser().getUsername());
  }

  //7. 유저 프로필 생성
  @Transactional
  public String createProfile(UserProfileRequestDto requestDto, String username){
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new IllegalArgumentException("일치하지 않습니다.")
    );
    user.update(requestDto);
    userRepository.save(user);
    return "생성이 완료되었습니다.";
  }

//  @Transactional
//  public String createProfile(MultipartFile file, UserProfileRequestDto requestDto, User user){
//    String saveLocal = "local";
//    String saveImageName = user.getUsername();
//    String updateImageData= saveLocal+saveImageName;
//    user.changeProfile(requestDto.getPassword(), requestDto.getRegion(), requestDto.getHeight(),
//        requestDto.getWeight(), updateImageData);
//    fileService.upload(file, saveImageName);
//    userRepository.save(user);
//    return "생성이 완료되었습니다.";
//  }


  //8.유저 프로필 조회
  @Transactional
  public UserProfileResponseDto getUserProfile(String username){
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new IllegalArgumentException("연결상태 불량입니다 다시 유저 조회해주시기 바랍니다.")
    );
    return UserProfileResponseDto.from(user);
  }

  //9. 유저 프로필 수정
  @Transactional
  public UserProfileResponseDto updateProfile(String username){
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new IllegalArgumentException("연결상태 불량입니다 다시 유저 조회해주시기 바랍니다.")
    );
    return UserProfileResponseDto.from(user);
  }


}