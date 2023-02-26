package com.sparta.hotbody.report.dto;

import com.sparta.hotbody.report.entity.CommentReportHistory;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentReportResponseDto {
  private String reporterUsername;
  private Long reportedCommentId;
  private String content;
  private LocalDateTime createdAt;

  public CommentReportResponseDto(CommentReportHistory commentReportHistory) {
    this.reporterUsername = commentReportHistory.getReporterUserName();
    this.reportedCommentId = commentReportHistory.getReportedCommentId();
    this.content = commentReportHistory.getContent();
    this.createdAt = commentReportHistory.getCreatedAt();
  }

}
