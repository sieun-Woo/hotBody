package com.sparta.hotbody.report.service;

import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.report.dto.PostReportRequestDto;
import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.repository.PostReportRepository;
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
public class PostReportService {

  public final PostRepository postRepository;

  public final PostReportRepository postReportHistoryRepository;

  @Transactional
  public PostReportResponseDto reportPost(User reporter, PostReportRequestDto postReportRequestDto) {
    
    Post reportedPost = postRepository.findById(postReportRequestDto.getReportedPostId()).orElseThrow(RuntimeException::new);
    PostReportHistory postReportHistory = createPostReportHistory(reporter, reportedPost, postReportRequestDto);
    postReportHistory.setReportCount(postReportHistoryRepository.findByReportedPostId(postReportRequestDto.getReportedPostId()).size());

    return new PostReportResponseDto(postReportHistory);
  }


  private PostReportHistory createPostReportHistory(User reporter, Post reported, PostReportRequestDto postReportRequestDto) {
    PostReportHistory postReportHistory = new PostReportHistory(reporter, reported, postReportRequestDto.getContent());
    postReportHistoryRepository.save(postReportHistory);
    return postReportHistory;
  }


  public Page<PostReportResponseDto> getAllReportedPosts(int page, int size, String sortBy, boolean isAsc) {
    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<PostReportHistory> reportedPosts = postReportHistoryRepository.findAll(pageable);
    Page<PostReportResponseDto> postReportResponseDtos = reportedPosts.map(p -> new PostReportResponseDto(p));

    return postReportResponseDtos;
  }


}
