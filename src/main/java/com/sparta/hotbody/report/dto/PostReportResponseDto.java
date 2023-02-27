package com.sparta.hotbody.report.dto;

import com.sparta.hotbody.report.entity.PostReportHistory;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class PostReportResponseDto {
  private String reporterUsername;
  private Long reportedPostId;
  private String content;
  private LocalDateTime createdAt;

  public PostReportResponseDto(PostReportHistory postReportHistory) {
    this.reporterUsername = postReportHistory.getReporterUserName();
    this.reportedPostId = postReportHistory.getReportedPostId();
    this.content = postReportHistory.getContent();
    this.createdAt = postReportHistory.getCreatedAt();
  }


}
