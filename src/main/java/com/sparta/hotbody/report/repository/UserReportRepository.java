package com.sparta.hotbody.report.repository;

import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.entity.UserReportHistory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReportHistory, String> {

  boolean existsByReporterUsernameAndReportedUsername(String reporterUsername, String reportedUsername);

  List<UserReportHistory> findByReportedUsername(String reportedUsername);

  Page<UserReportHistory> findAll(Pageable pageable);

}
