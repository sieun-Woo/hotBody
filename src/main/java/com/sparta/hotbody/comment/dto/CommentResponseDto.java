package com.sparta.hotbody.comment.dto;

import com.sparta.hotbody.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

  private String nickname;

  private String content;

  private Integer likes;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  public CommentResponseDto(Comment comment) {
    this.nickname = comment.getNickname();
    this.content = comment.getContent();
    this.likes = comment.getLikes();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
  }
}
