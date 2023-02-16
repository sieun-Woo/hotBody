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
    validateUserReportRequest(reporter, reportRequestDto); // 자기 자신 혹은 중복 신고인지 확인
    User reported = userRepository.findByUsername(reportRequestDto.getReportedUsername()).orElseThrow(RuntimeException::new);
    ReportHistory userReportHistory = createUserReportHistory(reporter, reported, reportRequestDto);
    userReportHistory.setReportCount(userReportHistoryRepository.findByReportedUsername(reportRequestDto.getReportedUsername()).size());
    checkUserStatusIsBeingReported(reported, reportRequestDto);
    return new ReportResponseDto(userReportHistory.getReporter().getUsername(), reported.getUsername(), reportRequestDto.getContent(), userReportHistory.getCreatedAt());
  }

  private void checkUserStatusIsBeingReported(User reported, ReportRequestDto reportRequestDto) {
    if (userReportHistoryRepository.findByReportedUsername(reportRequestDto.getReportedUsername()).size() //특정 유저의 신고된 횟수가 3이상 이면
        >= NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED) {
      reported.reportedUserChangeRole(); // 역할을 REPORTED(신고된 유저)로 변경
    }
  }

  private ReportHistory createUserReportHistory(User reporter, User reported, ReportRequestDto reportRequestDto) {
    ReportHistory userReportHistory = new ReportHistory(reporter, reported, reportRequestDto.getContent());
    userReportHistoryRepository.save(userReportHistory);
    return userReportHistory;
  }

  private void validateUserReportRequest(User reporter, ReportRequestDto reportRequestDto) {
    if (reporter.getUsername().equals(reportRequestDto.getReportedUsername())) {
      throw new NotSelfReportException();
    }
    if (userReportHistoryRepository.existsByReporterUsernameAndReportedUsername(reporter.getUsername(), reportRequestDto.getReportedUsername())) {
      throw new AlreadyReportException();
    }
  }


}
