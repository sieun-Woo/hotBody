package com.sparta.hotbody.post.dto;

import com.sparta.hotbody.post.entity.Post;
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

  public PostResponseDto(Post post) {
    this.nickname = post.getNickname();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.image = post.getImage();
    this.likes = post.getLikes();
    this.createdAt = post.getCreatedAt();
    this.modifiedAt = post.getModifiedAt();
  }
}


