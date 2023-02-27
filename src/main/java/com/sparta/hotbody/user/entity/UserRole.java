package com.sparta.hotbody.user.entity;

public enum UserRole {
  USER(Authority.USER),  // 사용자 권한
  TRAINER(Authority.TRAINER),  // 판매자 권한
  ADMIN(Authority.ADMIN),   // 관리자 권한
  REPORTED(Authority.REPORTED),  // 신고 누적자, 불량 유저
  REPORTED_TRAINER(Authority.REPORTED_TRAINER); // 신고 누적 트레이너, 불량 트레이너



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
    public static final String REPORTED = "ROLE_REPORTED";
    public static final String REPORTED_TRAINER = "ROLE_REPORTED_TRAINER";
  }
}
