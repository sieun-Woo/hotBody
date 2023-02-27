package com.sparta.hotbody.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentReportRequestDto {

  private Long reportedCommentId;
  private String content;

}
