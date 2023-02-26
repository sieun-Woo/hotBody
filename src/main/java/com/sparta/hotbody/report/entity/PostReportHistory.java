package com.sparta.hotbody.report.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class PostReportHistory extends TimeStamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User reporter;

  private String reporterUserName;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Post reportedPost;
  private Long reportedPostId;

  @Column(nullable = false)
  private String content;

  @Column
  private int reportCount;


  public PostReportHistory(User reporter, Post reportedPost, String content) {
    this.reporterUserName = reporter.getUsername();
    this.reportedPostId = reportedPost.getId();
    this.reporter = reporter;
    this.reportedPost = reportedPost;
    this.content = content;
    this.reportCount = getReportCount();
  }
}
