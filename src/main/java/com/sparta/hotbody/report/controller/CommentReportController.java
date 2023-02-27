package com.sparta.hotbody.report.controller;

import com.sparta.hotbody.report.dto.CommentReportRequestDto;
import com.sparta.hotbody.report.dto.CommentReportResponseDto;
import com.sparta.hotbody.report.service.CommentReportService;
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
@RequestMapping("/api/reports/comments")
public class CommentReportController {
  private final CommentReportService commentReportService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping
  public ResponseEntity reportUser(@RequestBody CommentReportRequestDto commentReportRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return new ResponseEntity(commentReportService.reportComment(userDetails.getUser(),
        commentReportRequestDto),HttpStatus.OK);
  }

  @GetMapping
  public Page<CommentReportResponseDto> getAllRepotedComments(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ){
    return commentReportService.getAllReportedComments (page,size,sortBy, isAsc);
  }


}
