package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.common.page.PageDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {

  ResponseEntity permitTrainer(Long userId);

  ResponseEntity getRegistrations(PageDto pageDto);

  ResponseEntity refuseTrainer(Long userId);
}
