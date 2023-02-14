package com.sparta.hotbody.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeRequestDto {

  private Long postId;

  private Long userId;
}
