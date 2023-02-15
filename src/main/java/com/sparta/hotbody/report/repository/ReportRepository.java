package com.sparta.hotbody.report.repository;

import com.sparta.hotbody.report.entity.ReportHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportHistory, Long> {

  boolean existsByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);

  List<ReportHistory> findByReportedUserId(Long reportedId);

  void deleteAllByReportedUserId(Long id);
}
