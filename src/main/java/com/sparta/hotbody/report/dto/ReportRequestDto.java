package com.sparta.hotbody.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportRequestDto {
  private String reportedUsername;
  private String content;
}
