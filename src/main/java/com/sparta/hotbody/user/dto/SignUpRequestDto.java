package com.sparta.hotbody.user.dto;

import java.util.Date;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {


  //정규식이 틀렸을때 발생하는 예외 MethodArgumentNotValidException
  @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
  private String username;
  @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])^[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자(~!@#$%^&*()+|=)")
  private String password;
  private String image;
  private String nickname;
  private String gym;
  private Integer gender; // 0남자 1여자
  private Date birthday;
  private int height;
  private int weight;
  private boolean admin = false;
  private String adminToken = "";
  private boolean isSeller = false;


  @Builder
  public SignUpRequestDto(String username, String nickname, String password, String gym,
      Integer gender, Date birthday, int height, int weight, String image, boolean admin) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
    this.gym = gym;
    this.gender = gender;
    this.weight = weight;
    this.height = height;
    this.birthday = birthday;
    this.image = image;
    this.admin = admin;
  }
}