package com.sparta.hotbody.user.repository;

import com.sparta.hotbody.user.entity.Promote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoteRepository extends JpaRepository<Promote, Long> {

  Optional<Promote> findByUserUsername(String username);
  void deleteByUserUsername(String username);
  List<Promote> findAllByIsPromoted(Integer isPromoted);

  //기준 없이 전부다 가져온다.
  Page<Promote> findAll(Pageable pageable);

}