package com.sparta.hotbody.report.controller;

import com.sparta.hotbody.report.dto.ReportRequestDto;
import com.sparta.hotbody.report.dto.ReportResponseDto;
import com.sparta.hotbody.report.service.ReportService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReportController {

  private final ReportService reportService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/reports/users")
  public ResponseEntity reportUser(@Valid @RequestBody ReportRequestDto reportRequestDto) {
    return new ResponseEntity(reportService.reportUser(userReportRequest),HttpStatus.OK);
  }

}


