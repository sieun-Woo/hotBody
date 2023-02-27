package com.sparta.hotbody.exerciseRecord.repository;

import com.sparta.hotbody.exerciseRecord.entity.ExerciseRecord;
import com.sparta.hotbody.report.entity.PostReportHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
  Page<ExerciseRecord> findAll(Pageable pageable);
}
