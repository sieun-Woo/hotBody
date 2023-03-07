package com.sparta.hotbody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {

  ALREADY_LOGIN_EXCEPTION(200, "이미 로그인 되었습니다."),

  AUTHENTICATED_EXCEPTION(401, "인증에 실패했습니다."),
  USERNAME_PASSWORD_DO_NOT_MATCH(401,"아이디와 비빌번호를 확인바랍니다."),
  PASSWORD_DO_NOT_MATCH(401,"비빌번호가 일치하지 않습니다."),

  AUTHORIZED_EXCEPTION(403, "인가에 실패했습니다."),
  NOT_USER(403,"현재 사용자는 USER 권한이 없습니다."),
  NOT_TRAINER(403,"현재 사용자는 TRAINER 권한이 없습니다."),
  NOT_REPORTED(403,"신고된 게정이 아닙니다."),
  NOT_REPORTED_TRAINER(403,"신고된 트레이너 게정이 아닙니다."),
  USERNAME_IS_NOT_CORRECT(403,
      "아이디는 4자 이상, 10자 이하의 숫자, 소문자 알파벳으로 구성되어야 합니다."),
  PASSWORD_IS_NOT_CORRECT(403,
      "비밀번호는 8자 이상, 15자 이하의 숫자, 소문자 알파벳이 포함되어야 합니다."),
  ADMIN_CODE_IS_NOT_CORRECT(403, "관리자 코드가 일치하지 않습니다."),
  WRITER_IS_NOT_CORRECT(403, "글쓴이만 수정할 수 있습니다."),
  ADDED_LIKE(403, "이미 좋아요를 눌렀습니다."),
  CANCELED_LIKE(403, "이미 좋아요가 취소되었습니다."),
  ALREADY_LOGOUT(403, "이미 로그아웃 되었습니다."),

  USER_IS_NOT_EXIST(404, "사용자가 존재하지 않습니다."),
  TRAINER_IS_NOT_EXIST(404, "트레이너가 존재하지 않습니다."),
  ADMIN_IS_NOT_EXIST(404, "관리자가 존재하지 않습니다."),
  EMAIL_IS_NOT_EXIST(404, "이메일이 존재하지 않습니다."),
  POST_IS_NOT_EXIST(404, "게시글이 존재하지 않습니다."),
  COMMENT_IS_NOT_EXIST(404, "댓글이 존재하지 않습니다."),
  APPLY_IS_NOT_EXIST(404, "요청이 존재하지 않습니다."),
  PAGINATION_IS_NOT_EXIST(404, "검색 결과가 없습니다."),
  ID_OR_EMAIL_IS_NOT_EXIST(404, "요청하신 페이지가 존재하지 않습니다."),
  DIET_IS_NOT_EXIST(404, "식단이 존재하지 않습니다."),
  EXERCISE_RECORD_IS_NOT_EXIST(404, "운동 기록이 존재하지 않습니다."),

  USERNAME_IS_EXIST(409, "이미 등록된 아이디입니다.");

  private final int statusCode;
  private final String message;

}
