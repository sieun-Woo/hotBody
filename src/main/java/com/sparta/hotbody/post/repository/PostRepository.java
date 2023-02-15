package com.sparta.hotbody.post.repository;

import com.sparta.hotbody.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 게시글 전체 조회
  Page<Post> findAll(Pageable pageable);

  // 제목으로 검색
  Page<Post> findByTitleContaining(String searchKeyword, Pageable pageable);

  // 내용으로 검색
  Page<Post> findByContentContaining(String searchKeyword, Pageable pageable);

  // 닉네임으로 검색
  Page<Post> findByNicknameContaining(String searchKeyword, Pageable pageable);
}
