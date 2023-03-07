package com.sparta.hotbody.report.entity;

import com.sparta.hotbody.comment.entity.Comment;
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
public class CommentReportHistory extends TimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User reporter;

  private String reporterNickname;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Comment reportedComment;
  private Long reportedCommentId;

  @Column(nullable = false)
  private String content;

  @Column
  private int reportCount;


  public CommentReportHistory(User reporter, Comment reportedComment, String content) {
    this.reporterNickname = reporter.getNickname();
    this.reportedCommentId = reportedComment.getId();
    this.reporter = reporter;
    this.reportedComment = reportedComment;
    this.content = content;
    this.reportCount = getReportCount();
  }
}