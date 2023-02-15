package com.sparta.hotbody.report.service;

import com.sparta.hotbody.report.dto.ReportRequestDto;
import com.sparta.hotbody.report.dto.ReportResponseDto;
import com.sparta.hotbody.report.entity.ReportHistory;
import com.sparta.hotbody.report.exception.AlreadyReportException;
import com.sparta.hotbody.report.exception.NotSelfReportException;
import com.sparta.hotbody.report.repository.ReportRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportService {
  private final static int NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED = 3;
  public final ReportRepository reportRepository;
  public final UserRepository userRepository;
  
  public final ReportRepository userReportHistoryRepository;

  @Transactional
  public ReportResponseDto reportUser(User reporter, ReportRequestDto reportRequestDto) {
    validateUserReportRequest(reporter, reportRequestDto);
    User reported = userRepository.findById(reportRequestDto.getReportedUserId()).orElseThrow(RuntimeException::new);
    ReportHistory userReportHistory = createUserReportHistory(reporter, reported, reportRequestDto);
    checkUserStatusIsBeingReported(reported, reportRequestDto);
    return new ReportResponseDto(userReportHistory.getId(), EditRequestDto.toDto(reported),
        reportRequestDto.getContent(),
        userReportHistory.getCreatedAt());
  }

  private void checkUserStatusIsBeingReported(User reported, ReportRequestDto reportRequestDto) {
    if (userReportHistoryRepository.findByReportedUserId(reportRequestDto.getReportedUserId()).size()
        >= NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED) {
      reported.setStatusIsBeingReported();
    }
  }

  private ReportHistory createUserReportHistory(User reporter, User reported, ReportRequestDto reportRequestDto) {
    ReportHistory userReportHistory = new ReportHistory(reporter.getId(), reported.getId(),
        reportRequestDto.getContent());
    userReportHistoryRepository.save(userReportHistory);
    return userReportHistory;
  }

  private void validateUserReportRequest(User reporter, ReportRequestDto reportRequestDto) {
    if (reporter.isReportMySelf(reportRequestDto.getReportedUserId())) {
      throw new NotSelfReportException();
    }

    if (userReportHistoryRepository.existsByReporterIdAndReportedUserId(reporter.getId(),
        reportRequestDto.getReportedUserId())) {
      throw new AlreadyReportException();
    }
  }
}
