package com.sparta.hotbody.report.controller;

import com.sparta.hotbody.report.dto.PostReportRequestDto;
import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.dto.UserReportRequestDto;
import com.sparta.hotbody.report.service.PostReportService;
import com.sparta.hotbody.report.service.UserReportService;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class PostReportController {

  private final PostReportService postReportService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/post")
  public PostReportResponseDto reportPost(@RequestBody PostReportRequestDto postReportRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postReportService.reportPost(userDetails.getUser(), postReportRequestDto);
  }

  @GetMapping("/posts")
  public Page<PostReportResponseDto> getAllReportedPosts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ){
    return postReportService.getAllReportedPosts (page-1,size,sortBy, isAsc);
  }

}
