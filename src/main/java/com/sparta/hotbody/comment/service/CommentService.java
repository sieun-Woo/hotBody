package com.sparta.hotbody.comment.service;

import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.comment.dto.CommentRequestDto;
import com.sparta.hotbody.comment.dto.CommentResponseDto;
import com.sparta.hotbody.comment.entity.Comment;

import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.exception.CustomException;
import com.sparta.hotbody.exception.ExceptionStatus;
import com.sparta.hotbody.post.dto.PostResponseDto;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.user.entity.User;
import java.io.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  // 1. 댓글 등록
  @Transactional
  public ResponseEntity<String> createComment(User user, CommentRequestDto requestDto, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    Comment comment = new Comment(requestDto, user, post);
    commentRepository.save(comment);
    return ResponseEntity.ok("댓글이 작성되었습니다.");
  }


  // 2. 댓글 전체 조회
  public Page<CommentResponseDto> getAllComments(GetPageModel getPageModel) {

    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<Comment> comments = commentRepository.findAll(pageable);
    Page<CommentResponseDto> commentResponseDto = comments.map(m -> new CommentResponseDto(m));

    return commentResponseDto;
  }

  // 3. 댓글 선택 조회
  public CommentResponseDto getComment(Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST)
    );

    CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
    return commentResponseDto;
  }

  // 4. 댓글 수정
  @Transactional
  public ResponseEntity<String> updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto,
      User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST)
    );
    if (comment.getUser().getId().equals(user.getId())) {
      comment.modifyComment(commentModifyRequestDto);
    } else {
      throw new CustomException(ExceptionStatus.WRITER_IS_NOT_CORRECT);
    }
    return ResponseEntity.ok("댓글 수정을 완료하였습니다.");
  }

  // 5. 댓글 삭제
  @Transactional
  public ResponseEntity<String> deleteComment(Long commentId, User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST)
    );
    log.info(String.valueOf(comment));
    if (comment.getUser().getId().equals(user.getId())) {
      commentRepository.delete(comment);
    } else {
      throw new CustomException(ExceptionStatus.WRITER_IS_NOT_CORRECT);
    }
    return ResponseEntity.ok("댓글 수정을 완료하였습니다.");
  }

  // 6. 해당 게시글 관련 댓글 전체 조회
  public Page<CommentResponseDto> getPostComments(Long postId, GetPageModel getPageModel) {

    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);
    Page<CommentResponseDto> commentResponseDto = comments.map(m -> new CommentResponseDto(m));

    return commentResponseDto;
  }

  // 9. 나의 댓글 조회
  public Page<CommentResponseDto> getMyAllComments(String nickname, GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<Comment> comments = commentRepository.findByNicknameContaining(nickname, pageable);
    Page<CommentResponseDto> commentResponseDto = comments.map(c -> new CommentResponseDto(c));
    return commentResponseDto;
  }

  // 10. 키워드로 나의 댓글 검색
  @Transactional
  public Page<CommentResponseDto> searchMyComments(
      String nickname, String searchType, String searchKeyword, GetPageModel getPageModel) {
    // 페이징 처리
    Pageable pageable = new PageDto().toPageable(getPageModel);
    Page<Comment> comments = commentRepository.findAllByNicknameContainingAndContentContaining
          (nickname, searchKeyword, pageable);
    return comments.map(c -> new CommentResponseDto(c));
  }
}
