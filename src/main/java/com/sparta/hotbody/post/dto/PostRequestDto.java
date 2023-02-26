package com.sparta.hotbody.post.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.sparta.hotbody.post.entity.PostCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {


  private String category;
  private String title;
  private String content;
  private String resourcePath;
}
