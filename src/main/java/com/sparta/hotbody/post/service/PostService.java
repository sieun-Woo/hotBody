package com.sparta.hotbody.post.service;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService extends TimeStamp {

  private final PostRepository postRepository;

  // 1. 게시글 등록
  @Transactional
  public void createPost(PostRequestDto postRequestDto, User user) {
    Post post = new Post(postRequestDto, user);
    postRepository.save(post);
  }

  // 2. 게시글 전체 조회
  @Transactional
  public List<PostResponseDto> getAllPosts(int page, int size, String sortBy, boolean isAsc) {
    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Post> posts = postRepository.findAll(pageable);
    List<PostResponseDto> postResponseDtoList = new ArrayList<>();

    for (Post post : posts) {
      PostResponseDto postResponseDto = new PostResponseDto(post.getNickname(), post.getTitle(),
          post.getContent(), post.getLikes());
      postResponseDtoList.add(postResponseDto);
    }
    return postResponseDtoList;
  }

  // 3. 게시글 선택 조회
  @Transactional
  public PostResponseDto getPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );

    PostResponseDto postResponseDto = new PostResponseDto(post.getNickname(), post.getTitle(),
        post.getContent(), post.getLikes());

    return postResponseDto;
  }

  // 4. 게시글 수정
  @Transactional
  public void updatePost(Long postId, PostModifyRequestDto postModifyRequestDto,
      User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    if (post.getNickname().equals(user)) {
      post.modifyPost(postModifyRequestDto);
      postRepository.save(post);
    } else {
      throw new IllegalArgumentException("게시글을 수정하려면 로그인이 필요합니다.");
    }
  }

  // 5. 게시글 삭제
  @Transactional
  public void deletePost(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    if (post.getNickname().equals(user)) {
      postRepository.delete(post);
    } else {
      throw new IllegalArgumentException("게시글을 삭제하려면 로그인이 필요합니다.");
    }
  }

  // 6. 게시글 좋아요 추가
  @Transactional
  public void okLikes(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    if (post.getNickname().equals(user)) {
//      post.setLikes(+1);
    } else {
      throw new IllegalArgumentException("");
    }
  }

  // 7. 게시글 좋아요 취소
  public void cancelLikes(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    if (post.getNickname().equals(user)) {
//      post.setLikes(-1);
    } else {
      throw new IllegalArgumentException("");
    }
  }
}
