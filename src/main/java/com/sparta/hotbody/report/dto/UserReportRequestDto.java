package com.sparta.hotbody.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserReportRequestDto {
  private String reportedNickname;
  private String content;
}
