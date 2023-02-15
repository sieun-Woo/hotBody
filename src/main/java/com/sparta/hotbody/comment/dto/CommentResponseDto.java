package com.sparta.hotbody.comment.dto;

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
}
