//package com.sparta.hotbody.common;
//
//import com.sparta.hotbody.admin.entity.Admin;
//import com.sparta.hotbody.admin.repository.AdminRepository;
//import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
//import com.sparta.hotbody.post.entity.Post;
//import com.sparta.hotbody.post.entity.PostCategory;
//import com.sparta.hotbody.post.repository.PostRepository;
//import com.sparta.hotbody.user.entity.User;
//import com.sparta.hotbody.user.entity.UserRole;
//import com.sparta.hotbody.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class InitData implements ApplicationRunner {
//
//  private final PasswordEncoder passwordEncoder;
//  private final UserRepository userRepository;
//  private final PostRepository postRepository;
//  private final AdminRepository adminRepository;
//
//  @Override
//  public void run(ApplicationArguments args){
//
//
//    Admin admin = new Admin("admin", passwordEncoder.encode("admin"), UserRole.ADMIN, "admin", "www.naver.com", "admin@gmail.com");
//    adminRepository.save(admin);
//    User user1 = new User("user1", passwordEncoder.encode("user1"), UserRole.USER, "user1_nickname", 1, 33, "aba@abc.com");
//    userRepository.save(user1);
//    User user2 = new User("user2", passwordEncoder.encode("user2"), UserRole.USER, "user2_nickname", 0, 44, "aba@abc.com");
//    userRepository.save(user2);
//    User user3 = new User("user3", passwordEncoder.encode("user3"), UserRole.TRAINER, "trainer1_nickname", 0, 55, "aba@abc.com");
//    userRepository.save(user3);
//    User user4 = new User("user4", passwordEncoder.encode("user4"), UserRole.TRAINER, "trainer2_nickname", 1, 66, "aba@abc.com");
//    userRepository.save(user4);
//    Post post1 = new Post("첫번째 게시글", "첫번째 글을 쓰다니! 영광입니다.", user1, PostCategory.DIET);
//    postRepository.save(post1);
//    Post post2 = new Post("두번째 게시글", "기분이 좋아 하나 더 씁니다!", user1, PostCategory.DIET);
//    postRepository.save(post2);
//    Post post3 = new Post("세번째 게시글", "세번째는 뺏기지 않았습니다.", user2, PostCategory.DIET);
//    postRepository.save(post3);
//    Post post4 = new Post("네번째 게시글", "네째는 뺏기지 않았습니다.", user1, PostCategory.EXERCISE);
//    postRepository.save(post4);
//    Post post5 = new Post("다섯번째 게시글", "다섯번째는 뺏기지 않았습니다.", user2, PostCategory.EXERCISE);
//    postRepository.save(post5);
//    Post post6 = new Post("여섯번째 게시글", "여섯번째는 뺏기지 않았습니다.", user1, PostCategory.EXERCISE);
//    postRepository.save(post6);
//    Post post7 = new Post("일곱번째 게시글", "안녕하세요. 첫 트레이너입니다.", user3, PostCategory.TALK);
//    postRepository.save(post7);
//    Post post8 = new Post("여덟번째 게시글", "안녕하세요. 두번째 트레이너입니다.", user4, PostCategory.TALK);
//    postRepository.save(post8);
//    Post post9 = new Post("아홉번째 게시글", "여러분도 할 수 있습니다.", user3, PostCategory.TALK);
//    postRepository.save(post9);
//    Post post10 = new Post("열번째 게시글", "삼두 이렇게 만들면 됩니다.", user4, PostCategory.TALK);
//    postRepository.save(post10);
//    Post post11 = new Post("열한번째 게시글", "난 트1이 좋더라.", user2, PostCategory.TRAINER);
//    postRepository.save(post11);
//    Post post12 = new Post("열두번째 게시글", "나는 트2가 좋음.", user1, PostCategory.TALK);
//    postRepository.save(post12);
//  }
//}