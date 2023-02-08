package com.sparta.hotbody.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

  private String nickname;

  private String title;

  private String content;

  private Integer likes;
}
