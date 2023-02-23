package com.sparta.hotbody.comment.service;

import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.comment.dto.CommentRequestDto;
import com.sparta.hotbody.comment.dto.CommentResponseDto;
import com.sparta.hotbody.comment.entity.Comment;

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
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  // 1. 댓글 등록
  @Transactional
  public void createComment(CommentRequestDto commentRequestDto, User user, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
    );

    Comment comment = new Comment(commentRequestDto, user);
    commentRepository.save(comment);
  }

  // 2. 댓글 전체 조회
  @Transactional
  public List<CommentResponseDto> getAllComments(int page, int size, String sortBy, boolean isAsc) {

    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Comment> comments = commentRepository.findAll(pageable);
    List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

    for (Comment comment : comments) {
      CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getNickname(),
          comment.getContent(), comment.getLikes(), comment.getCreatedAt(), comment.getModifiedAt());
      commentResponseDtoList.add(commentResponseDto);
    }
    return commentResponseDtoList;
  }

  // 3. 댓글 선택 조회
  @Transactional
  public CommentResponseDto getComment(Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
    );

    CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getNickname(),
        comment.getContent(), comment.getLikes(), comment.getCreatedAt(), comment.getModifiedAt());

    return commentResponseDto;
  }

  // 4. 댓글 수정
  @Transactional
  public void updateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto,
      User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
    );
    if (comment.getUser().getId().equals(user.getId())) {
      comment.modifyComment(commentModifyRequestDto);
      commentRepository.save(comment);
    } else {
      throw new IllegalArgumentException("댓글을 수정하려면 로그인이 필요합니다.");
    }
  }

  // 5. 댓글 삭제
  @Transactional
  public void deleteComment(Long commentId, User user) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
    );
    if (comment.getUser().getId().equals(user.getId())) {
      commentRepository.delete(comment);
    } else {
      throw new IllegalArgumentException("댓글을 삭제하려면 로그인이 필요합니다.");
    }
  }
}
