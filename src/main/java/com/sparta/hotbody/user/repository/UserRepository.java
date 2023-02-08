package com.sparta.hotbody.user.repository;

import com.sparta.hotbody.user.entity.Promote;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Page<User> findByRole(UserRole role, Pageable pageable);

  void deleteByUsername(String username);

}
