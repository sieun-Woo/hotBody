package com.sparta.hotbody.diet.repository;

import com.sparta.hotbody.diet.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DietRepository extends JpaRepository<Diet, Long> {

}
