package com.sparta.hotbody.report.entity;

import com.sparta.hotbody.common.TimeStamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ReportHistory extends TimeStamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long reporterId;

  @Column(nullable = false)
  private Long reportedUserId;

  @Column(nullable = false)
  private String content;

  public ReportHistory(Long reporterId, Long reportedUserId, String content) {
    this.reporterId = reporterId;
    this.reportedUserId = reportedUserId;
    this.content = content;
  }
}
