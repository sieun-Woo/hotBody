package com.sparta.hotbody.report.repository;

import com.sparta.hotbody.report.entity.PostReportHistory;
import com.sparta.hotbody.report.entity.UserReportHistory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReportHistory, Long> {
  Page<PostReportHistory> findAll(Pageable pageable);

  List<PostReportHistory> findByReportedPostId(Long reportedPostId);
}
