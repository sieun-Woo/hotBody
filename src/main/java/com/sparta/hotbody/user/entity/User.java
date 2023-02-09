package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // JOINED는 다른 DB 생성. SINGLE은 안에 둘 다 있음
@DiscriminatorColumn
@Entity(name = "users")
public class User extends TimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "User_ID")
  private Long id;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private Integer gender;
  @Column(nullable = false)
  private String age;
  @Column
  private int height;
  @Column
  private int weight;
  @Column
  private String image;
  @Column
  private String introduce;
  @Column(nullable = false)
  private String nickname;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRole role;
  @Column
  private String region;

//  public User(String username, String password, UserRole role, String nickname, Integer gender, String age) {
//    this.username = username;
//    this.password = password;
//    this.nickname = nickname;
//    this.gender = gender;
//    this.age = age;
//    this.role = role;
//  }

  public User(SignUpRequestDto requestDto, String password, UserRole role) {
    this.username = requestDto.getUsername();
    this.nickname = requestDto.getNickname();
    this.password = password;
    this.gender = requestDto.getGender();
    this.role = role;
    this.age = requestDto.getAge();
  }

  public void update(UserProfileRequestDto requestDto) {
    this.password = requestDto.getPassword();
    this.height = requestDto.getHeight();
    this.weight = requestDto.getWeight();
    this.region = requestDto.getRegion();
    this.image = requestDto.getImage();
  }

//  public void changeProfile(String password, String region, int height, int weight, String image) {
//    this.password = password;
//    this.region = region;
//    this.height = height;
//    this.weight = weight;
//    this.image = image;
//  }

}