package com.sparta.hotbody.report.dto;

import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.entity.UserReportHistory;
import com.sparta.hotbody.user.entity.UserRole;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class UserReportResponseDto {

  private String reporterNickname;
  private String reportedNickname;
  private String content;
  private LocalDateTime createdAt;
  private UserRole role;
  private Long userId;

  public UserReportResponseDto(UserReportHistory userReportHistory) {
    this.reporterNickname = userReportHistory.getReporterNickname();
    this.reportedNickname = userReportHistory.getReportedNickname();
    this.content = userReportHistory.getContent();
    this.createdAt = userReportHistory.getCreatedAt();
    this.role = userReportHistory.getReportedUser().getRole();
    this.userId = userReportHistory.getReportedUser().getId();
  }
}
