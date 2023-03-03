package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdResponseDto;
import com.sparta.hotbody.admin.dto.FindAdminPwRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminPwResponseDto;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.TrainerResponseDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import com.sparta.hotbody.user.dto.UsersResponseDto;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface AdminService {

  ResponseEntity signup(AdminSignUpRequestDto adminSignUpRequestDto);

  ResponseEntity login(LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request)
      throws UnsupportedEncodingException;

  ResponseEntity permitTrainer(Long requestId);

  Page<TrainerResponseDto> getRegistrations(int pageNum);

  ResponseEntity refuseTrainer(Long userId);

  ResponseEntity cancelTrainer(Long userId);

  ResponseEntity updatePost(Long postId, PostModifyRequestDto postModifyRequestDto);

  ResponseEntity deletePost(Long postId);

  ResponseEntity updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto);

  ResponseEntity deleteComment(Long commentId);

  Page<UsersResponseDto> getUserList(int pageNum);

  UserProfileResponseDto getUser(Long userId);

  Page<UsersResponseDto> getTrainerList(int pageNum);

  TrainerResponseDto getTrainer(Long trainerId);

  ResponseEntity updateUserInfo(Long userId, UserProfileRequestDto userProfileRequestDto);

  ResponseEntity deleteUser(Long userId);

  FindAdminIdResponseDto findAdminId(FindAdminIdRequestDto findAdminIdRequestDto)
      throws MessagingException;

  FindAdminPwResponseDto findAdminPw(FindAdminPwRequestDto findAdminPwRequestDto)
      throws MessagingException;

  ResponseEntity logout(HttpServletRequest request);

  ResponseEntity makeUserSuspended(Long userId);

  ResponseEntity makeTrainerSuspended(Long userId);

  ResponseEntity makeUserNormal(Long userId);

  ResponseEntity makeTrainerNormal(Long userId);
}
