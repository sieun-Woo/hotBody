package com.sparta.hotbody.common.jwt.entity;

import com.sparta.hotbody.admin.entity.Admin;
import com.sparta.hotbody.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String refreshToken;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Admin admin;

  public RefreshToken(String refreshToken, User user) {
    this.refreshToken = refreshToken;
    this.user = user;
  }

  public RefreshToken(String refreshToken, Admin admin) {
    this.refreshToken = refreshToken;
    this.admin = admin;
  }
}
