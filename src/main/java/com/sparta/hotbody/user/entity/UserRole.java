package com.sparta.hotbody.user.entity;

public enum UserRole {
  USER(Authority.USER),  // 사용자 권한
  TRAINER(Authority.TRAINER),  // 판매자 권한
  ADMIN(Authority.ADMIN);  // 관리자 권한
  private final String authority;
  UserRole(String authority) {
    this.authority = authority;
  }
  public String getAuthority() {
    return this.authority;
  }
  public static class Authority {
    public static final String USER  = "ROLE_USER";
    public static final String TRAINER = "ROLE_TRAINER";
    public static final String ADMIN = "ROLE_ADMIN";
  }
}
