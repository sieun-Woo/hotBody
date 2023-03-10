package com.sparta.hotbody.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
  private Long id;
  private String email = "default@Email.com";
  private String nickname;

  public KakaoUserInfoDto(Long id, String nickname, String email) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
  }

}
