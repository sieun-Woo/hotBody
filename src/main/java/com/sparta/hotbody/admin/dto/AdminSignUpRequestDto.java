package com.sparta.hotbody.admin.dto;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSignUpRequestDto {

  //정규식이 틀렸을때 발생하는 예외 MethodArgumentNotValidException
  @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
  private String username;
  @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])^[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자(~!@#$%^&*()+|=)")
  private String password;
  @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
  private String nickname;
  private String email;
  private String adminToken = "";
  private String image;

  @Builder
  public AdminSignUpRequestDto(String username, String nickname, String password, String email, String image, String adminToken) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
    this.image = image;
    this.adminToken = adminToken;
  }
}
