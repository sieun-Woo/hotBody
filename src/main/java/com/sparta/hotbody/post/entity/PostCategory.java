package com.sparta.hotbody.post.entity;




public enum PostCategory {

  EXERCISE, // 운동
  DIET, // 식단
  TRAINER, // 트레이너
  TALK; // 잡담

  private final String category;

  PostCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return this.category;
  }
  public static class Category {
    public static final String USER  = "ROLE_USER";
    public static final String TRAINER = "ROLE_TRAINER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String REPORTED = "ROLE_REPORTED";
  }
}
