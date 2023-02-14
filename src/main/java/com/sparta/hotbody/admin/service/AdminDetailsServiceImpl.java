package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.admin.entity.Admin;
import com.sparta.hotbody.admin.repository.AdminRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

  private final AdminRepository adminRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Admin admin = adminRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found "));
    return new AdminDetailsImpl(admin, admin.getUsername());
  }

}