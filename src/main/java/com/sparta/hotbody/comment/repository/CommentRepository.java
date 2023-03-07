package com.sparta.hotbody.comment.repository;

import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Page<Comment> findAll(Pageable pageable);
  Page<Comment> findAllByPostId(Long postId, Pageable pageable);

  Page<Comment> findByNicknameContaining(String nickname, Pageable pageable);

  Page<Comment> findAllByNicknameContainingAndContentContaining(String nickname, String searchKeyword, Pageable pageable);
}
