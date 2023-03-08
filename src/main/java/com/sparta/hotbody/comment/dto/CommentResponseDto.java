package com.sparta.hotbody.comment.dto;

import com.sparta.hotbody.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CommentResponseDto {
  private Long id;
  private String nickname;

  private String content;

  private Integer likes;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private Long postId;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.nickname = comment.getNickname();
    this.content = comment.getContent();
    this.likes = comment.getCommentLikeList().size();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
    this.postId = comment.getPost().getId();
  }
}
