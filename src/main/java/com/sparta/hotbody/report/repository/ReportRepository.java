package com.sparta.hotbody.report.repository;

import com.sparta.hotbody.report.entity.ReportHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportHistory, String> {

  boolean existsByReporterUsernameAndReportedUsername(String reporterUsername, String reportedUsername);

  List<ReportHistory> findByReportedUsername(String reportedUsername);

}
