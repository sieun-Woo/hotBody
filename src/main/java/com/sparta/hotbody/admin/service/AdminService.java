package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminIdResponseDto;
import com.sparta.hotbody.admin.dto.FindAdminPwRequestDto;
import com.sparta.hotbody.admin.dto.FindAdminPwResponseDto;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.user.dto.LoginRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AdminService {

  ResponseEntity signup(AdminSignUpRequestDto adminSignUpRequestDto);

  ResponseEntity login(LoginRequestDto loginRequestDto, HttpServletResponse response)
      throws UnsupportedEncodingException;

  ResponseEntity permitTrainer(Long requestId);

  ResponseEntity getRegistrations(PageDto pageDto);

  ResponseEntity refuseTrainer(Long userId);

  ResponseEntity cancelTrainer(Long userId);

  ResponseEntity updatePost(Long postId, PostModifyRequestDto postModifyRequestDto);

  ResponseEntity deletePost(Long postId);

  ResponseEntity updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto);

  ResponseEntity deleteComment(Long commentId);

  ResponseEntity getUserList(PageDto pageDto);

  ResponseEntity getUser(Long userId);

  ResponseEntity getTrainerList(PageDto pageDto);

  ResponseEntity getTrainer(Long trainerId);

  ResponseEntity updateUserInfo(Long userId, UserProfileRequestDto userProfileRequestDto);

  ResponseEntity deleteUser(Long userId);

  FindAdminIdResponseDto findAdminId(FindAdminIdRequestDto findAdminIdRequestDto);

  FindAdminPwResponseDto findAdminPw(FindAdminPwRequestDto findAdminPwRequestDto);

  ResponseEntity logout(UserDetailsImpl userDetails);
}
