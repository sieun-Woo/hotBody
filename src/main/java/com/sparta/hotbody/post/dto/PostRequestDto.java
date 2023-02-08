package com.sparta.hotbody.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

  private String nickname;

  private String title;

  private String content;
}
