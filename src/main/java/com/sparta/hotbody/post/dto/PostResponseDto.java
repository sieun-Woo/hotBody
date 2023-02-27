package com.sparta.hotbody.post.dto;

import com.sparta.hotbody.comment.dto.CommentResponseDto;
import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.post.entity.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {

  private Long id;
  private 

  private String nickname;

  private String title;

  private String content;

  private String image;

  private Integer likes;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  public PostResponseDto(Post post) {
    this.id = post.getId();
    this.nickname = post.getNickname();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.image = post.getImage();
    this.createdAt = post.getCreatedAt();
    this.modifiedAt = post.getModifiedAt();
    this.likes = post.getPostLikeList().size();
  }
}


