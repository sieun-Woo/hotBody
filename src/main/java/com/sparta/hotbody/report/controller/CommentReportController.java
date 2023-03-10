package com.sparta.hotbody.report.controller;

import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.report.dto.CommentReportRequestDto;
import com.sparta.hotbody.report.dto.CommentReportResponseDto;
import com.sparta.hotbody.report.dto.UserReportResponseDto;
import com.sparta.hotbody.report.service.CommentReportService;
import com.sparta.hotbody.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/report")
public class CommentReportController {
  private final CommentReportService commentReportService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/comment")
  @PreAuthorize("hasAnyRole('USER', 'TRAINER', 'ADMIN', 'REPORTED', 'REPORTED_TRAINER')")
  public CommentReportResponseDto reportComment(
      @RequestBody CommentReportRequestDto commentReportRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return commentReportService.reportComment(userDetails.getUser(), commentReportRequestDto);
  }

  @GetMapping("/comments")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<CommentReportResponseDto> getAllReportedComments(GetPageModel getPageModel){
    return commentReportService.getAllReportedComments (getPageModel);
  }

}
