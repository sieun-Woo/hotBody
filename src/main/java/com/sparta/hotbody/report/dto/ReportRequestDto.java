package com.sparta.hotbody.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportRequestDto {
  private Long reportedUserId;
  private String content;
}
