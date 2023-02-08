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

  @Override
  public ResponseEntity updatePost(Long postId) { // TODO: PostRequestDto 추가
//    Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
//    post.update(postRequestDto);
//    postRepository.save(post);
    return new ResponseEntity("게시글 수정을 완료하였습니다.", HttpStatus.OK);
  }

  @Override
  public ResponseEntity deletePost(Long postId) {
//    Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
//    postRepository.delete(post);
    return new ResponseEntity("게시글 삭제가 완료되었습니다.", HttpStatus.OK);
  }

  @Override
  public ResponseEntity updateComment(Long commentId) { // TODO: CommentRequestDto 추가
//    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
//    comment.update(commentRequestDto);
//    commentRepository.save(comment);
    return new ResponseEntity("댓글 수정을 완료하였습니다.", HttpStatus.OK);
  }

  @Override
  public ResponseEntity deleteComment(Long commentId) {
//    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
//    commentRepository.delete(comment);
    return new ResponseEntity("댓글 삭제가 완료되었습니다.", HttpStatus.OK);
  }

  @Override
  public ResponseEntity getUserList(PageDto pageDto) {
//    Page<User> userPage = userRepository.findAllByRole("USER", pageDto.toPageable());
//    Page<UserResponseDto> userResponseDtoPage = UserResponseDto.toDtoPage(userPage);
    return new ResponseEntity(HttpStatus.OK); // TODO: userResponseDtoPage 추가
  }

  @Override
  public ResponseEntity getUser(Long userId) {
//    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    return new ResponseEntity(HttpStatus.OK); // TODO: UserResponseDto 추가
  }

  @Override
  public ResponseEntity getTrainerList(PageDto pageDto) {
//    Page<User> userPage = userRepository.findAllByRole("TRAINER", pageDto.toPageable());
//    Page<UserResponseDto> userResponseDtoPage = UserResponseDto.toDtoPage(userPage);
    return new ResponseEntity(HttpStatus.OK); // TODO: userResponseDtoPage 추가
  }

  @Override
  public ResponseEntity getTrainer(Long trainerId) {
//    User user = userRepository.findById(trainerId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    return new ResponseEntity(HttpStatus.OK); // TODO: TrainerResponseDto 추가
  }

  @Override
  public ResponseEntity updateUserInfo(Long userId) { // TODO: UserRequestDto 추가
//    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
//    user.update(userRequestDto);
//    userRepository.save(user);
    return new ResponseEntity("사용자 정보 수정하였습니다.", HttpStatus.OK);
  }

  @Override
  public ResponseEntity deleteUser(Long userId) {
//    User user = userRepository.findeById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
//    userRepository.delete(user);
    return new ResponseEntity("사용자 계정를 삭제하였습니다.", HttpStatus.OK);
  }
}
