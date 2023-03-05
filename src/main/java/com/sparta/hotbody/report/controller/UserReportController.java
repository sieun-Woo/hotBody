package com.sparta.hotbody.report.controller;

import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.dto.UserReportRequestDto;
import com.sparta.hotbody.report.dto.UserReportResponseDto;
import com.sparta.hotbody.report.repository.PostReportRepository;
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
@RequestMapping("/api/report")
public class UserReportController {

  private final UserReportService userReportService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/user")
  public UserReportResponseDto reportUser(@RequestBody UserReportRequestDto userReportRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return userReportService.reportUser(userDetails.getUser(), userReportRequestDto);
  }

  @GetMapping("/users")
  public Page<UserReportResponseDto> getAllReportedUsers(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc
  ){
    return userReportService.getAllReportedUsers (page-1,size,sortBy, isAsc);
  }

}


