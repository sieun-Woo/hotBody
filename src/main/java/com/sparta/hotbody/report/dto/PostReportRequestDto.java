package com.sparta.hotbody.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostReportRequestDto {
  private Long reportedPostId;
  private String content;

}
