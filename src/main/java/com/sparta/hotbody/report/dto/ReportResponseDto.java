package com.sparta.hotbody.report.dto;

import com.sparta.hotbody.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ReportResponseDto {

  private String reporterUsername;
  private String reportedUsername;
  private String content;
  private LocalDateTime createdAt;

}
