package com.sparta.hotbody.post.repository;

import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 게시글 전체 조회
  Page<Post> findAllByCategory(PostCategory postCategory,Pageable pageable);


  // 제목으로 검색
  Page<Post> findByTitleContaining(String searchKeyword, Pageable pageable);

  // 내용으로 검색
  Page<Post> findByContentContaining(String searchKeyword, Pageable pageable);

  // 닉네임으로 검색
  Page<Post> findByNicknameContaining(String searchKeyword, Pageable pageable);

  // 제목으로 검색
  Page<Post> findByCategoryAndTitleContaining(PostCategory postCategory, String searchKeyword, Pageable pageable);

  // 내용으로 검색
  Page<Post> findByCategoryAndContentContaining(PostCategory postCategory, String searchKeyword, Pageable pageable);

  // 닉네임으로 검색
  Page<Post> findByCategoryAndNicknameContaining(PostCategory postCategory, String searchKeyword, Pageable pageable);

  // 닉네임과 제목으로 검색
  Page<Post> findAllByNicknameContainingAndTitleContaining(String nickname, String searchKeyword, Pageable pageable);

  // 닉네임과 내용으로 검색
  Page<Post> findAllByNicknameContainingAndContentContaining(String nickname, String searchKeyword, Pageable pageable);
}
