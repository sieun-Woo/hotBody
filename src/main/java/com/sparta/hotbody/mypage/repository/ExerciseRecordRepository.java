package com.sparta.hotbody.mypage.repository;

import com.sparta.hotbody.mypage.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {

}
