package com.sparta.hotbody.report.service;

import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.comment.repository.CommentRepository;
import com.sparta.hotbody.report.dto.CommentReportRequestDto;
import com.sparta.hotbody.report.dto.CommentReportResponseDto;
import com.sparta.hotbody.report.entity.CommentReportHistory;
import com.sparta.hotbody.report.repository.CommentReportRepository;
import com.sparta.hotbody.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentReportService {

  public final CommentRepository commentRepository;

  public final CommentReportRepository commentReportHistoryRepository;

  @Transactional
  public CommentReportResponseDto reportComment(User reporter,
      CommentReportRequestDto commentReportRequestDto) {

    Comment reportedComment = commentRepository.findById(
        commentReportRequestDto.getReportedCommentId()).orElseThrow(RuntimeException::new);
    CommentReportHistory commentReportHistory = createCommentReportHistory(reporter,
        reportedComment, commentReportRequestDto);
    commentReportHistory.setReportCount(commentReportHistoryRepository.findByReportedCommentId(
        commentReportRequestDto.getReportedCommentId()).size());

    return new CommentReportResponseDto(commentReportHistory);
  }


  private CommentReportHistory createCommentReportHistory(User reporter, Comment reported,
      CommentReportRequestDto commentReportRequestDto) {
    CommentReportHistory commentReportHistory = new CommentReportHistory(reporter, reported,
        commentReportRequestDto.getContent());
    commentReportHistoryRepository.save(commentReportHistory);
    return commentReportHistory;
  }

  public Page<CommentReportResponseDto> getAllReportedComments(int page, int size, String sortBy, boolean isAsc) {
    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<CommentReportHistory> reportedComments = commentReportHistoryRepository.findAll(pageable);
    Page<CommentReportResponseDto> commentReportResponseDtos = reportedComments.map(p -> new CommentReportResponseDto(p));

    return commentReportResponseDtos;
  }
}
