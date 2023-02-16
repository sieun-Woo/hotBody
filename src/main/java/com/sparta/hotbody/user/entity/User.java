package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.admin.dto.AdminSignUpRequestDto;
import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.common.TimeStamp;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import com.sparta.hotbody.user.dto.UserProfileResponseDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
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
  private int age;
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

  // 유저와 게시글의 연관 관계(1 : N)
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> postList = new ArrayList<>();

  // 유저와 댓글의 연관 관계(1 : N)
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList = new ArrayList<>();

  public User(String username, String password, UserRole role, String nickname, Integer gender, int age) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.gender = gender;
    this.role = role;
    this.age = age;
  }

  public User(SignUpRequestDto signUpRequestDto, String password,UserRole role) {
    this.username = signUpRequestDto.getUsername();
    this.nickname = signUpRequestDto.getNickname();
    this.password = password;
    this.gender = signUpRequestDto.getGender();
    this.role = role;
    this.age = signUpRequestDto.getAge();
  }

  public void update(UserProfileRequestDto requestDto) {
    this.height = requestDto.getHeight();
    this.weight = requestDto.getWeight();
    this.region = requestDto.getRegion();
    this.image = requestDto.getImage();
  }

  public void update(UserProfileRequestDto requestDto, String resourcePath) {
    this.height = requestDto.getHeight();
    this.weight = requestDto.getWeight();
    this.region = requestDto.getRegion();
    this.image = resourcePath;
  }

    public void TrainerPermission(String introduce) {
    this.introduce = introduce;
    this.role = UserRole.TRAINER;
  }

  public void cancelPermission() {
    this.role = UserRole.USER;
  }

  // 신고가 누적된 유저 역할 변경
  public void reportedUserChangeRole() { this.role = UserRole.REPORTED; }

}