package com.sparta.hotbody.post.repository;

import com.sparta.hotbody.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);

  Page<Post> findByTitleContaining(String searchKeyword, Pageable pageable);

  Page<Post> findByTitleAndContentContaining(String searchKeyword, Pageable pageable);

  Page<Post> findByNicknameContaining(String searchKeyword, Pageable pageable);
}
