package com.sparta.hotbody.exerciseRecord.repository;

import com.sparta.hotbody.exerciseRecord.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {

}
