package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.registration.entity.Registration;
import com.sparta.hotbody.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminserviceImpl implements AdminService {

  // ToDo: 유저 레포지토리 연결 필요
//  private final UserRepository userRepository;
  // ToDo: 게시글 레포지토리 연결 필요
//  private final UserRepository userRepository;
  // ToDo: 댓글 레포지토리 연결 필요
//  private final UserRepository userRepository;
  private final RegistrationRepository registrationRepository;

  @Override
  public ResponseEntity getRegistrations(PageDto pageDto) {
    Page<Registration> RequestList = registrationRepository.findAll(pageDto.toPageable());
    return new ResponseEntity(RequestList, HttpStatus.OK);
  }

  @Override
  public ResponseEntity permitTrainer(Long userId) {
//    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
//    if(user.getRole() == USER) {
//      Request request = requestRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("요청이 존재하지 않습니다."));
//      user.changeRoleToTrainer();
//    }
    return new ResponseEntity("트레이너 권한을 부여하였습니다.", HttpStatus.OK);
  }

  @Override
  public ResponseEntity refuseTrainer(Long userId) {
    Registration registration = registrationRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    registrationRepository.delete(registration);
    return new ResponseEntity("트레이너 요청이 삭제되었습니다.", HttpStatus.OK);
  }
}
