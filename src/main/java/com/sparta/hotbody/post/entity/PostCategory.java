package com.sparta.hotbody.post.entity;




public enum PostCategory {

  EXERCISE(Category.EXERCISE), // 운동
  DIET(Category.DIET), // 식단
  TRAINER(Category.TRAINER), // 트레이너
  TALK(Category.TALK); // 잡담

  private final String category;

  PostCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return this.category;
  }
  public static class Category {
    public static final String EXERCISE  = "CATEGORY_EXERCISE";
    public static final String DIET = "CATEGORY_DIET";
    public static final String TRAINER = "CATEGORY_TRAINER";
    public static final String TALK = "CATEGORY_TALK";
  }
}
