package com.sparta.hotbody.report.dto;

import com.sparta.hotbody.report.entity.CommentReportHistory;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentReportResponseDto {
  private String reporterNickname;
  private Long reportedCommentId;
  private String content;
  private LocalDateTime createdAt;
  private String writerNickname;
  private String commentContent;

  public CommentReportResponseDto(CommentReportHistory commentReportHistory) {
    this.reporterNickname = commentReportHistory.getReporterNickname();
    this.reportedCommentId = commentReportHistory.getReportedCommentId();
    this.content = commentReportHistory.getContent();
    this.createdAt = commentReportHistory.getCreatedAt();
    this.writerNickname = commentReportHistory.getReporter().getNickname();
    this.commentContent = commentReportHistory.getReportedComment().getContent();
  }

}
