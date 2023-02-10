package com.sparta.hotbody.user.repository;

import com.sparta.hotbody.user.entity.Trainer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoteRepository extends JpaRepository<Trainer, Long> {

  Optional<Trainer> findByUserUsername(String username);
  void deleteByUserUsername(String username);

  //기준 없이 전부다 가져온다.
  Page<Trainer> findAll(Pageable pageable);

}