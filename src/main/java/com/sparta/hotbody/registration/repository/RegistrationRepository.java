package com.sparta.hotbody.registration.repository;

import com.sparta.hotbody.registration.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  Page<Registration> findAll(Pageable pageable);
}
