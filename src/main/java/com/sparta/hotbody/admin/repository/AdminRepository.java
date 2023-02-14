package com.sparta.hotbody.admin.repository;

import com.sparta.hotbody.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  Optional<Admin> findByUsername(String Username);
}
