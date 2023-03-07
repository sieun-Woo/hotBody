package com.sparta.hotbody.user.entity;

import com.sparta.hotbody.comment.entity.Comment;
import com.sparta.hotbody.common.timestamp.TimeStamp;
import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.entity.PostLike;
import com.sparta.hotbody.user.dto.SignUpRequestDto;
import com.sparta.hotbody.user.dto.UserProfileRequestDto;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
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
  @Column(nullable = true)
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
  @Column(nullable = false, unique = true)
  private String nickname;

  // 아이디, 비밀번호 찾기에 사용할 이메일 주소
  @Column
  private String email;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRole role;
  @Column
  private String region;

  private Long kakaoId;

  // 유저와 게시글의 연관 관계(1 : N)
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> postList = new ArrayList<>();

  // 유저와 댓글의 연관 관계(1 : N)
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList = new ArrayList<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Trainer trainer;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> postLikeList = new ArrayList<>();

  @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrainerLike> trainerLikeList = new ArrayList<>();


  public User(String username, String password, UserRole role, String nickname, Integer gender, int age, String email) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.gender = gender;
    this.role = role;
    this.age = age;
    this.email = email;
  }

  //카카오 아이디
  public User(String username, Long kakaoId, String nickname, String password, String email, UserRole role) {
    this.username = username;
    this.kakaoId = kakaoId;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
    this.role = role;
  }

  public User(SignUpRequestDto signUpRequestDto, String password,UserRole role) {
    this.username = signUpRequestDto.getUsername();
    this.nickname = signUpRequestDto.getNickname();
    this.email = signUpRequestDto.getEmail();
    this.password = password;
    this.gender = signUpRequestDto.getGender();
    this.role = role;
    this.age = signUpRequestDto.getAge();
  }

  public void update(UserProfileRequestDto requestDto) {
    this.nickname = requestDto.getNickname();
    this.height = requestDto.getHeight();
    this.weight = requestDto.getWeight();
    this.region = requestDto.getRegion();
  }

  public void updateImage(String resourcePath) {
    this.image = resourcePath;
  }

    public void TrainerPermission(String introduce) {
    this.introduce = introduce;
    this.role = UserRole.TRAINER;
  }


  //카카오 아이디 업데이트
  public User kakaoIdUpdate(Long kakaoId) {
    this.kakaoId = kakaoId;
    return this;
  }

  public void cancelPermission() {
    this.role = UserRole.USER;
  }

  // 비밀번호를 임시 비밀번호로 변경
  public void modifyPassword(String encodePassword) {
    this.password = encodePassword;
  }

  // 신고가 누적된 유저 역할 변경
  public void reportedUserChangeRole() { this.role = UserRole.REPORTED; }

  // 정상 유저로 역할 변경
  public void changeUserRoleNormal() {this.role = UserRole.USER;}

  public void changeUserRoleReportedTrainer() {this.role = UserRole.REPORTED_TRAINER;}

  public void changeUserRoleTrainer() {this.role = UserRole.TRAINER;}

}