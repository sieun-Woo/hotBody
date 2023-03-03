package com.sparta.hotbody.common.jwt.entity;

import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {

  @Id
  private String id;

  @Indexed
  private String refreshToken;

  public RefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
