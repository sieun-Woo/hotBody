package com.sparta.hotbody.report.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(nullable = false)
  private User reporter;

  private String reporterUserName;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User reportedUser;
  private String reportedUsername;

  @Column(nullable = false)
  private String content;

  @Column
  private int reportCount;


  public ReportHistory(User reporter, User reportedUser, String content) {
    this.reporterUserName = reporter.getUsername();
    this.reportedUsername = reportedUser.getUsername();
    this.reporter = reporter;
    this.reportedUser = reportedUser;
    this.content = content;
    this.reportCount = getReportCount();
  }
}
