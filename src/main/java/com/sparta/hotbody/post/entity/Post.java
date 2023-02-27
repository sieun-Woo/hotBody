package com.sparta.hotbody.post.entity;

import com.sparta.hotbody.comment.dto.CommentRequestDto;
import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.post.dto.PostModifyRequestDto;
import com.sparta.hotbody.post.dto.PostRequestDto;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name = "post")
public class Post extends TimeStamp {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;
  @Column(nullable = false)
  private String nickname;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String content;
  @Column
  private String image;
  @Column(nullable = false)
  private int likes = 0;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private PostCategory category;

  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  // 게시글과 유저의 연관 관계(N : 1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  // 게시글과 댓글의 연관 관계(1 : N)
  @OrderBy(value = "modifiedAt desc")
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList = new ArrayList<>();

  // 게시글과 게시글 좋아요의 연관 관계(1 : N)
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> postLikeList = new ArrayList<>();

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  public Post(PostRequestDto postRequestDto, User user) {
    this.user = user;
    this.image = postRequestDto.getResourcePath();
    this.nickname = user.getNickname();
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
    this.category = PostCategory.valueOf(postRequestDto.getCategory());
  }

  public Post(String title, String content, User user, PostCategory category) {
    this.category = category;
    this.user = user;
    this.nickname = user.getNickname();
    this.title = title;
    this.content = content;
  }

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  public void modifyPost(PostModifyRequestDto postModifyRequestDto) {
    this.title = postModifyRequestDto.getTitle();
    this.content = postModifyRequestDto.getContent();
  }

  public void plusLikes() {
    this.likes += 1;
  }

  public void minusLikes() {
    this.likes -= 1;
  }

  public void updateImage(String resourcePath) {
    this.image = resourcePath;
  }

}

