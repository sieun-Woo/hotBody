package com.sparta.hotbody.user.dto;

import com.sparta.hotbody.user.entity.Trainer;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;


@Getter
public class UsersResponseDto {
  private Long id;
  private String nickname;
  private int gender;
  private int age;
  private String introduce;
  private String region;
  private String email;
  private UserRole role;
  private Integer likes;

  public UsersResponseDto(){}

  public UsersResponseDto(User user){
    this.id = user.getId();
    this.nickname = user.getNickname();
    this.gender = user.getGender();
    this.age = user.getAge();
    this.region = user.getRegion();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.introduce = user.getIntroduce();
    this.likes=user.getTrainerLikeList().size();
  }

  @Builder
  public UsersResponseDto(Long id, String nickname, int gender, int age, String region, String email, UserRole role, String introduce){
    this.id = id;
    this.nickname = nickname;
    this.gender = gender;
    this.age = age;
    this.region = region;
    this.email = email;
    this.role = role;
    this.introduce = introduce;
  }

  public Page<UsersResponseDto> toDtoPage(Page<User> userPage) {
    Page<UsersResponseDto> usersResponseDtoPage = userPage
        .map(m -> UsersResponseDto.builder()
            .id(m.getId())
            .nickname(m.getNickname())
            .gender(m.getGender())
            .age(m.getAge())
            .region(m.getRegion())
            .role(m.getRole())
            .introduce(m.getIntroduce())
            .build());
    return usersResponseDtoPage;
  };
}
