package com.sparta.hotbody.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

  private String nickname;

  private String title;

  private String content;

  private String image;

  private Integer likes;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;


}


