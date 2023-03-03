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

  ResponseEntity<String> signup(AdminSignUpRequestDto adminSignUpRequestDto);

  ResponseEntity<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request)
      throws UnsupportedEncodingException;

  ResponseEntity<String> permitTrainer(Long requestId);

  Page<TrainerResponseDto> getRegistrations(int pageNum);

  ResponseEntity<String> refuseTrainer(Long userId);

  ResponseEntity<String> cancelTrainer(Long userId);

  ResponseEntity<String> updatePost(Long postId, PostModifyRequestDto postModifyRequestDto);

  ResponseEntity<String> deletePost(Long postId);

  ResponseEntity<String> updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto);

  ResponseEntity<String> deleteComment(Long commentId);

  Page<UsersResponseDto> getUserList(int pageNum);

  UserProfileResponseDto getUser(Long userId);

  Page<UsersResponseDto> getTrainerList(int pageNum);

  TrainerResponseDto getTrainer(Long trainerId);

  ResponseEntity<String> updateUserInfo(Long userId, UserProfileRequestDto userProfileRequestDto);

  ResponseEntity<String> deleteUser(Long userId);

  FindAdminIdResponseDto findAdminId(FindAdminIdRequestDto findAdminIdRequestDto)
      throws MessagingException;

  FindAdminPwResponseDto findAdminPw(FindAdminPwRequestDto findAdminPwRequestDto)
      throws MessagingException;

  ResponseEntity<String> logout(HttpServletRequest request);

  ResponseEntity<String> makeUserSuspended(Long userId);

  ResponseEntity<String> makeTrainerSuspended(Long userId);

  ResponseEntity<String> makeUserNormal(Long userId);

  ResponseEntity<String> makeTrainerNormal(Long userId);
}
