package com.sparta.hotbody.report.repository;

import com.sparta.hotbody.report.entity.CommentReportHistory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReportHistory, Long> {
  Page<CommentReportHistory> findAll(Pageable pageable);

  List<CommentReportHistory> findByReportedCommentId(Long reportedCommentId);
}
