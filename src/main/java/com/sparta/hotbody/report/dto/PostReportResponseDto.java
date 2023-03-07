package com.sparta.hotbody.report.dto;

import com.sparta.hotbody.post.entity.PostCategory;
import com.sparta.hotbody.report.entity.PostReportHistory;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class PostReportResponseDto {
  private String reporterNickname;
  private String title;
  private Long reportedPostId;
  private String content;
  private LocalDateTime createdAt;
  private PostCategory category;
  private Long postId;

  public PostReportResponseDto(PostReportHistory postReportHistory) {
    this.reporterNickname = postReportHistory.getReporterNickname();
    this.title = postReportHistory.getReportedPost().getTitle();
    this.reportedPostId = postReportHistory.getReportedPostId();
    this.content = postReportHistory.getContent();
    this.createdAt = postReportHistory.getCreatedAt();
    this.category = postReportHistory.getReportedPost().getCategory();
    this.postId = postReportHistory.getReportedPostId();
  }


}
