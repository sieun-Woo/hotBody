package com.sparta.hotbody.common;

import com.sparta.hotbody.post.entity.Post;
import com.sparta.hotbody.post.repository.PostRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitData implements ApplicationRunner {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  @Override
  public void run(ApplicationArguments args){

    User admin = new User("admin", passwordEncoder.encode("admin"), UserRole.ADMIN, "admin", 0, 22);
    User user1 = new User("user1", passwordEncoder.encode("user1"), UserRole.USER, "user1", 1, 33);
    User user2 = new User("user2", passwordEncoder.encode("user2"), UserRole.USER, "user2", 0, 44);
    User user3 = new User("user3", passwordEncoder.encode("user3"), UserRole.TRAINER, "trainer1", 0, 55);
    User user4 = new User("user4", passwordEncoder.encode("user4"), UserRole.TRAINER, "trainer2", 1, 66);
    userRepository.save(admin);
    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.save(user3);
    userRepository.save(user4);
    Post post1 = new Post("첫번째 게시글", "첫번째 글을 쓰다니! 영광입니다.", user1);
    Post post2 = new Post("두번째 게시글", "기분이 좋아 하나 더 씁니다!", user1);
    Post post3 = new Post("세번째 게시글", "세번째는 뺏기지 않았습니다.", user2);
    Post post4 = new Post("네번째 게시글", "네째는 뺏기지 않았습니다.", user1);
    Post post5 = new Post("다섯번째 게시글", "다섯번째는 뺏기지 않았습니다.", user2);
    Post post6 = new Post("여섯번째 게시글", "여섯번째는 뺏기지 않았습니다.", user1);
    postRepository.save(post1);
    postRepository.save(post2);
    postRepository.save(post3);
    postRepository.save(post4);
    postRepository.save(post5);
    postRepository.save(post6);
  }
}