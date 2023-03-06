package com.sparta.hotbody.post.service;

import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostLike;
import com.sparta.hotbody.post.repository.PostLikeRepository;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

  private final PostRepository postRepository;

  private final PostLikeRepository postLikeRepository;

  // 6. 게시글 좋아요 추가
  public ResponseEntity<String> pushLike(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if (postLikeRepository.existsByPostIdAndUserId(postId, user.getId())) {
      throw new CustomException(ExceptionStatus.ADDED_LIKE);
    }
    PostLike postLike = new PostLike(post, user);
    postLikeRepository.countAllByPostId(postId);
    postLikeRepository.save(postLike);
    return ResponseEntity.ok("좋아요를 눌렀습니다.");
  }

  // 7. 게시글 좋아요 취소
  public ResponseEntity<String> cancelLike(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if (!postLikeRepository.existsByPostIdAndUserId(postId, user.getId())) {
      throw new CustomException(ExceptionStatus.CANCELED_LIKE);
    }
    postLikeRepository.deleteByPostIdAndUserId(postId, user.getId());
    return ResponseEntity.ok("좋아요를 취소하였습니다.");
  }
}
