package com.sparta.hotbody.post.service;

import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.dto.PostSearchRequestDto;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostCategory;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.service.UploadService;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UploadService uploadService;

  // 1. 게시글 등록
  @Transactional
  public ResponseEntity<String> createPost(PostRequestDto postRequestDto,
      UserDetailsImpl userDetails) {
    User user = userDetails.getUser();
    Post post = new Post(postRequestDto, user);
    postRepository.saveAndFlush(post);

    return new ResponseEntity<>("작성 완료", HttpStatus.OK);
  }

  @Transactional
  public String createImage(MultipartFile file)
      throws IOException {
    Image image = uploadService.storeFile(file);
    String resourcePath = image.getResourcePath();
    return resourcePath;
  }


  // 2. 게시글 전체 조회
  public Page<PostResponseDto> getAllPosts(PostCategory postCategory, int page, int size,
      String sortBy, boolean isAsc) {
    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Post> posts = postRepository.findAllByCategory(postCategory, pageable);
    Page<PostResponseDto> postResponseDto = posts.map(p -> new PostResponseDto(p));

    return postResponseDto;
  }

  // 3. 게시글 선택 조회
  @Transactional
  public PostResponseDto getPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    PostResponseDto postResponseDto = new PostResponseDto(post);

    return postResponseDto;
  }

  // 키워드로 게시글 검색
  public Page<PostResponseDto> searchPost(
      PostCategory postCategory, String searchType, String searchKeyword,
      int page, int size, String sortBy, boolean isAsc) {
    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    if(postCategory == null) {
      if (searchType.equals("title")) {
        Page<Post> posts = postRepository.findByTitleContaining(
            searchKeyword, pageable);
        return posts.map(post -> new PostResponseDto(post));
      }

      if (searchType.equals("content")) {
        Page<Post> posts = postRepository.findByContentContaining(
            searchKeyword, pageable);
        return posts.map(post -> new PostResponseDto(post));
      }

      if (searchType.equals("nickname")) {
        Page<Post> posts = postRepository.findByNicknameContaining(
            searchKeyword, pageable);
        return posts.map(post -> new PostResponseDto(post));
      }
      return null;
    }

    if (searchType.equals("title")) {
      Page<Post> posts = postRepository.findByCategoryAndTitleContaining(
          postCategory, searchKeyword, pageable);
      return posts.map(post -> new PostResponseDto(post));
    }

    if (searchType.equals("content")) {
      Page<Post> posts = postRepository.findByCategoryAndContentContaining(
          postCategory, searchKeyword, pageable);
      return posts.map(post -> new PostResponseDto(post));
    }

    if (searchType.equals("nickname")) {
      Page<Post> posts = postRepository.findByCategoryAndNicknameContaining(
          postCategory, searchKeyword, pageable);
      return posts.map(post -> new PostResponseDto(post));
    }
    return null;
  }



  // 5. 게시글 삭제
  @Transactional
  public void deletePost(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    if (post.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRole.ADMIN)) {
      if (post.getImage() != null) {
        uploadService.remove(post.getImage());
      }
      postRepository.delete(post);
    } else {
      throw new IllegalArgumentException("게시글을 삭제하려면 로그인이 필요합니다.");
    }
  }

  // 4. 게시글 수정
  @Transactional
  public void updatePost(Long postId, PostModifyRequestDto postModifyRequestDto,
      User user) {

    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );

    if (post.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRole.ADMIN)) {
      post.modifyPost(postModifyRequestDto);
      postRepository.save(post);
    } else {
      throw new IllegalArgumentException("게시글을 수정하려면 로그인이 필요합니다.");
    }
  }

  // 6. 게시글 이미지 수정
  @Transactional
  public ResponseEntity<String> modifyImage(Long postId, MultipartFile file)
      throws IOException {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );
    if(post.getImage() != null) {
      uploadService.remove(post.getImage());
    }
    Image image = uploadService.storeFile(file);
    String resourcePath = image.getResourcePath();
    post.updateImage(resourcePath);
    return new ResponseEntity<>("수정되었습니다.", HttpStatus.OK);
  }
}
