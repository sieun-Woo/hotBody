package com.sparta.hotbody.user.repository;

import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);


  Page<User> findAllByRole(UserRole role, Pageable pageable);

  Page<User> findAllByRoleOrRole(UserRole role1, UserRole role2, Pageable pageable);


  // 이메일로 아이디 찾기
  Optional<User> findByEmail(String email);

  // 아이디와 이메일로 비밀번호 찾기
  Optional<User> findByUsernameAndEmail(String username, String email);

  void deleteByUsername(String username);

}
