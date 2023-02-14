package com.sparta.hotbody.comment.repository;

import com.sparta.hotbody.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

  boolean existsByCommentIdAndUserId(Long commentId, Long userId);

  void deleteByCommentIdAndUserId(Long commentId, Long userId);
}
