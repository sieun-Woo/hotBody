package com.sparta.hotbody.post.service;

import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostCategory;
import com.sparta.hotbody.post.repository.PostLikeRepository;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.service.UploadService;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import java.io.IOException;
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
    postRepository.save(post);
    return ResponseEntity.ok("작성 완료");
  }

  @Transactional
  public String createImage(MultipartFile file)
      throws IOException {
    Image image = uploadService.storeFile(file);
    String resourcePath = image.getResourcePath();
    return resourcePath;
  }

  // 2. 게시글 전체 조회
  public Page<PostResponseDto> getAllPosts(GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<Post> posts = postRepository.findAll(pageable);
    Page<PostResponseDto> postResponseDto = posts.map(p -> new PostResponseDto(p));

    return postResponseDto;
  }

  // 2-1 게시글 전체 조회
  public Page<PostResponseDto> getCategoryAllPosts(PostCategory postCategory, GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<Post> posts = postRepository.findAllByCategory(postCategory, pageable);
    Page<PostResponseDto> postResponseDto = posts.map(p -> new PostResponseDto(p));

    return postResponseDto;
  }

  // 3. 게시글 선택 조회
  public PostResponseDto getPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    PostResponseDto postResponseDto = new PostResponseDto(post);

    return postResponseDto;
  }

  // 키워드로 게시글 검색
  public Page<PostResponseDto> searchPost(
      PostCategory postCategory, String searchType, String searchKeyword, GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<Post> posts = null;

    if(postCategory == null) {
      if (searchType.equals("title")) {
        posts = postRepository.findByTitleContaining(
            searchKeyword, pageable);
      } else if (searchType.equals("content")) {
        posts = postRepository.findByContentContaining(
            searchKeyword, pageable);
      } else {
        posts = postRepository.findByNicknameContaining(
            searchKeyword, pageable);
      }
    } else {
      if (searchType.equals("title")) {
        posts = postRepository.findByCategoryAndTitleContaining(
            postCategory, searchKeyword, pageable);
      } else if (searchType.equals("content")) {
        posts = postRepository.findByCategoryAndContentContaining(
            postCategory, searchKeyword, pageable);
      } else {
        posts = postRepository.findByCategoryAndNicknameContaining(
            postCategory, searchKeyword, pageable);
      }
    }
    return toPage(posts);
  }

  private Page<PostResponseDto> toPage(Page<Post> posts) {
    return posts.map(post -> new PostResponseDto(post));
  }

  // 5. 게시글 삭제
  @Transactional
  public ResponseEntity<String> deletePost(Long postId, User user) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if (post.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRole.ADMIN)) {
      if (post.getImage() != null) {
        uploadService.remove(post.getImage());
      }
      postRepository.delete(post);
    } else {
      throw new CustomException(ExceptionStatus.NOT_USER);
    }
    return ResponseEntity.ok("게시글 삭제가 완료되었습니다.");
  }

  // 4. 게시글 수정
  @Transactional
  public ResponseEntity<String> updatePost(Long postId, PostModifyRequestDto postModifyRequestDto,
      User user) {

    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );

    if (post.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRole.ADMIN)) {
      post.modifyPost(postModifyRequestDto);

    } else {
      throw new CustomException(ExceptionStatus.NOT_USER);
    }
    return ResponseEntity.ok("게시글 수정이 완료되었습니다.");
  }

  // 6. 게시글 이미지 수정
  @Transactional
  public ResponseEntity<String> modifyImage(Long postId, MultipartFile file)
      throws IOException {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if(post.getImage() != null) {
      uploadService.remove(post.getImage());
    }
    Image image = uploadService.storeFile(file);
    String resourcePath = image.getResourcePath();
    post.updateImage(resourcePath);
    return ResponseEntity.ok("이미지가 수정되었습니다.");
  }

  // 7. 나의 게시글 조회
  @Transactional
  public Page<PostResponseDto> getMyAllPosts(
      String nickname, GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<Post> posts = postRepository.findByNicknameContaining(nickname, pageable);
    Page<PostResponseDto> postResponseDto = posts.map(p -> new PostResponseDto(p));
    return postResponseDto;
  }

  // 8. 키워드로 나의 게시글 검색
  @Transactional
  public Page<PostResponseDto> searchMyPosts(
      String nickname, String searchType, String searchKeyword, GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    if (searchType.equals("title")) {
      Page<Post> posts = postRepository.findAllByNicknameContainingAndTitleContaining
          (nickname, searchKeyword, pageable);
      return posts.map(post -> new PostResponseDto(post));
    } else {
      Page<Post> posts = postRepository.findAllByNicknameContainingAndContentContaining
          (nickname, searchKeyword, pageable);
      return posts.map(post -> new PostResponseDto(post));
    }
  }
}
