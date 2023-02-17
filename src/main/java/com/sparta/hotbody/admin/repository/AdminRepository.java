package com.sparta.hotbody.admin.repository;

import com.sparta.hotbody.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  Optional<Admin> findByUsername(String Username);

  // 이메일로 아이디 찾기
  Optional<Admin> findByEmail(String email);

  // 아이디와 이메일로 비밀번호 찾기
  Optional<Admin> findByUsernameAndEmail(String username, String email);
}
