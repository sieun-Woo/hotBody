package com.sparta.hotbody.report.service;

import com.sparta.hotbody.common.GetPageModel;
import com.sparta.hotbody.common.page.PageDto;
import com.sparta.hotbody.report.dto.PostReportResponseDto;
import com.sparta.hotbody.report.dto.UserReportRequestDto;
import com.sparta.hotbody.report.dto.UserReportResponseDto;
import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.entity.UserReportHistory;
import com.sparta.hotbody.report.exception.AlreadyReportException;
import com.sparta.hotbody.report.exception.NotSelfReportException;
import com.sparta.hotbody.report.repository.UserReportRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserReportService {
  private final static int NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED = 3;
  public final UserReportRepository userReportRepository;
  public final UserRepository userRepository;
  public final UserReportRepository userReportHistoryRepository;

  @Transactional
  public UserReportResponseDto reportUser(User reporter, UserReportRequestDto userReportRequestDto) {
    validateUserReportRequest(reporter, userReportRequestDto); // 자기 자신 혹은 중복 신고인지 확인
    User reported = userRepository.findByUsername(userReportRequestDto.getReportedUsername()).orElseThrow(RuntimeException::new);
    UserReportHistory userReportHistory = createUserReportHistory(reporter, reported,
        userReportRequestDto);
    userReportHistory.setReportCount(userReportHistoryRepository.findByReportedUsername(
        userReportRequestDto.getReportedUsername()).size());
    checkUserStatusIsBeingReported(reported, userReportRequestDto);
    return new UserReportResponseDto(userReportHistory);
  }

  private void checkUserStatusIsBeingReported(User reported, UserReportRequestDto userReportRequestDto) {
    if (userReportHistoryRepository.findByReportedUsername(userReportRequestDto.getReportedUsername()).size() //특정 유저의 신고된 횟수가 3이상 이면
        >= NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED) {
      reported.reportedUserChangeRole(); // 역할을 REPORTED(신고된 유저)로 변경
    }
  }

  private UserReportHistory createUserReportHistory(User reporter, User reported, UserReportRequestDto userReportRequestDto) {
    UserReportHistory userReportHistory = new UserReportHistory(reporter, reported, userReportRequestDto.getContent());
    userReportHistoryRepository.save(userReportHistory);
    return userReportHistory;
  }

  private void validateUserReportRequest(User reporter, UserReportRequestDto userReportRequestDto) {
    if (reporter.getUsername().equals(userReportRequestDto.getReportedUsername())) {
      throw new NotSelfReportException();
    }
    if (userReportHistoryRepository.existsByReporterUsernameAndReportedUsername(reporter.getUsername(), userReportRequestDto.getReportedUsername())) {
      throw new AlreadyReportException();
    }
  }


  public Page<UserReportResponseDto> getAllReportedUsers(GetPageModel getPageModel) {

    Pageable pageable = new PageDto().toPageable(getPageModel);

    Page<UserReportHistory> reportedUsers = userReportHistoryRepository.findAll(pageable);

    Page<UserReportResponseDto> userReportResponseDtos = reportedUsers.map(u -> new UserReportResponseDto(u));

    return userReportResponseDtos;
  }
}
