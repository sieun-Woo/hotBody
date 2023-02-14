package com.sparta.hotbody.comment.service;

import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.comment.entity.CommentLike;
import com.sparta.hotbody.comment.repository.CommentLikeRepository;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

  private final CommentRepository commentRepository;

  private final CommentLikeRepository commentLikeRepository;

  // 6. 댓글 좋아요 추가
  @Transactional
  public void okLikes(Long commentId, User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
    );
    if (commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId())) {
      throw new IllegalArgumentException("이미 좋아요 버튼을 눌렀습니다.");
    }
    CommentLike commentLike = new CommentLike(comment, user);
    comment.plusLikes();
    commentLikeRepository.save(commentLike);
  }

  // 7. 댓글 좋아요 취소
  @Transactional
  public void cancelLikes(Long commentId, User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
    );
    if (!commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId())) {
      throw new IllegalArgumentException("이미 좋아요가 취소되었습니다.");
    }
    commentLikeRepository.deleteByCommentIdAndUserId(commentId, user.getId());
    comment.minusLikes();
  }
}
