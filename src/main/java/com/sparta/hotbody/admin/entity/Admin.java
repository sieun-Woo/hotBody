package com.sparta.hotbody.admin.entity;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.user.entity.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "admin")
@Getter
@NoArgsConstructor
public class Admin {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String nickname;

  private String image;

  // 아이디, 비밀번호 찾기에 사용할 이메일 주소
  @Column
  private String email;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRole role;

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  public Admin(String username, String password, UserRole role, String nickname, String image, String email) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.email = email;
    this.image = image;
    this.role = role;
  }

  public Admin(AdminSignUpRequestDto adminSignUpRequestDto, String password, UserRole role) {
    this.username = adminSignUpRequestDto.getUsername();
    this.password = password;
    this.nickname = adminSignUpRequestDto.getNickname();
    this.email = adminSignUpRequestDto.getEmail();
    this.image = adminSignUpRequestDto.getImage();
    this.role = role;
  }


/**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */


  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */


  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  //
  public void modifyPassword(String encodePassword) {
    this.password = encodePassword;
  }
}
