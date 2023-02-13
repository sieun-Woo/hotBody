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
  @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
  private String nickname;
  private Integer gender; // 0남자 1여자
  private boolean admin = false;
  private int age;
  private String adminToken = "";

  @Builder
  public SignUpRequestDto(String username, String nickname, String password, int age, boolean admin, Integer gender) {
    this.username = username;
    this.nickname = nickname;
    this.age = age;
    this.password = password;
    this.admin = admin;
    this.gender = gender;
  }
}