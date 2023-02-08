package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {

  ResponseEntity permitTrainer(Long userId);

  ResponseEntity getRegistrations(PageDto pageDto);

  ResponseEntity refuseTrainer(Long userId);

  ResponseEntity updatePost(Long postId, PostModifyRequestDto postModifyRequestDto);

  ResponseEntity deletePost(Long postId);

  ResponseEntity updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto); // TODO: CommentRequestDto 추가

  ResponseEntity deleteComment(Long commentId);

  ResponseEntity getUserList(PageDto pageDto);

  ResponseEntity getUser(Long userId);

  ResponseEntity getTrainerList(PageDto pageDto);

  ResponseEntity getTrainer(Long trainerId);

  ResponseEntity updateUserInfo(Long userId); // TODO: UserRequestDto 추가

  ResponseEntity deleteUser(Long userId);
}
