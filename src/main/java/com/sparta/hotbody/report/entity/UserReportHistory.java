package com.sparta.hotbody.report.entity;

import com.sparta.hotbody.common.timestamp.TimeStamp;
import com.sparta.hotbody.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserReportHistory extends TimeStamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User reporter;

  @Column
  private String reporterNickname;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User reportedUser;

  @Column
  private String reportedNickname;

  @Column(nullable = false)
  private String content;

  @Column
  private int reportCount;


  public UserReportHistory(User reporter, User reportedUser, String content) {
    this.reporterNickname = reporter.getNickname();
    this.reportedNickname = reportedUser.getNickname();
    this.reporter = reporter;
    this.reportedUser = reportedUser;
    this.content = content;
    this.reportCount = getReportCount();
  }
}
