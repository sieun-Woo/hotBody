package com.sparta.hotbody.report.repository;

import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.entity.UserReportHistory;
import com.sparta.hotbody.user.entity.UserRole;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReportHistory, String> {

  boolean existsByReporterNicknameAndReportedNickname(String reporterNickname, String reportedNickname);

  List<UserReportHistory> findByReportedNickname(String reportedNickname);

  Page<UserReportHistory> findAllByRole(UserRole role,Pageable pageable);

  Page<UserReportHistory> findByReportedNickname(String reportedNickname, Pageable pageable);

}
