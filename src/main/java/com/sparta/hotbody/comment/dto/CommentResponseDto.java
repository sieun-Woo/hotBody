package com.sparta.hotbody.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

  private String nickname;

  private String content;

  private Integer likes;
}
