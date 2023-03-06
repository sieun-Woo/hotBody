package com.sparta.hotbody.comment.service;

import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.comment.entity.CommentLike;
import com.sparta.hotbody.comment.repository.CommentLikeRepository;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

  private final CommentRepository commentRepository;
  private final CommentLikeRepository commentLikeRepository;

  // 6. 댓글 좋아요 추가
  public ResponseEntity<String> pushLike(Long commentId, User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST)
    );
    if (commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId())) {
      throw new CustomException(ExceptionStatus.ADDED_LIKE);
    }
    CommentLike commentLike = new CommentLike(comment, user);
    commentLikeRepository.countAllByCommentId(commentId);
    commentLikeRepository.save(commentLike);
    return ResponseEntity.ok("좋아요를 눌렀습니다.");
  }

  // 7. 댓글 좋아요 취소
  public ResponseEntity<String> cancelLike(Long commentId, User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST)
    );
    if (!commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId())) {
      throw new CustomException(ExceptionStatus.CANCELED_LIKE);
    }
    commentLikeRepository.deleteByCommentIdAndUserId(commentId, user.getId());
    return ResponseEntity.ok("좋아요를 취소했습니다.");
  }
}
