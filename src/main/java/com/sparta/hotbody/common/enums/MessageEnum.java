package com.sparta.hotbody.common.enums;

import lombok.Getter;

@Getter
public enum MessageEnum {

  //------------------------------------------------
  //------------------------------------------------
  // Trainer 등급변환 관련
  PROMOTE_USER("트레이너로 등급 변환에 성공했습니다. success promote"),
  DEGRADE_TRAINER("일반 유저로 등급 변환에 성공했습니다. success degrade"),

  //------------------------------------------------
  //------------------------------------------------
  //카테고리 관련
  CREATE_CATEGORY_SUCCESS("카테고리 생성에 성공했습니다. success category update"),
  DELETE_CATEGORY_SUCCESS("카테고리 삭제에 성공했습니다. successfully deleted category"),
  UPDATE_CATEGORY_FAIL("카테고리 변경에 실패했습니다. fail to update category");

  //------------------------------------------------
  //------------------------------------------------

  private final String message;
  MessageEnum(String message){
    this.message = message;
  }

}