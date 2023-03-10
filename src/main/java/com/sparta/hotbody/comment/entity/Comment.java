package com.sparta.hotbody.comment.entity;

import com.sparta.hotbody.comment.dto.CommentModifyRequestDto;
import com.sparta.hotbody.comment.dto.CommentRequestDto;
import com.sparta.hotbody.common.timestamp.TimeStamp;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.report.entity.CommentReportHistory;
import com.sparta.hotbody.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// lombok
@Getter
@Setter
@NoArgsConstructor

// jpa
@Entity
@Table(name = "comment")
public class Comment extends TimeStamp {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Integer likes = 0;
   /* 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  // 댓글과 유저의 연관 관계(N : 1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  // 댓글과 게시글의 연관 관계(N : 1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  // 댓글과 댓글 좋아요의 연관 관계(1 : N)
  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentLike> commentLikeList = new ArrayList<>();

  @OneToMany(mappedBy = "reportedComment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentReportHistory> commentReportHistories = new ArrayList<>();

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  public Comment(CommentRequestDto commentRequestDto, User user, Post post) {
    this.post = post;
    this.user = user;
    this.nickname = user.getNickname();
    this.content = commentRequestDto.getContent();
  }

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  public void modifyComment(CommentModifyRequestDto commentModifyRequestDto) {
    this.content = commentModifyRequestDto.getContent();
  }

}
