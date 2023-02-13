package com.sparta.hotbody.common;

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
  }
}