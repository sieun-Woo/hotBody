package com.sparta.hotbody.admin.service;

import com.sparta.hotbody.admin.entity.Admin;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminDetailsImpl implements UserDetails {

  private final Admin admin;

  private final String adminName;

  public AdminDetailsImpl(Admin admin, String adminName) {
    this.admin = admin;
    this.adminName = adminName;
  }

  public Admin getUser() {
    return admin;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    UserRole role = admin.getRole();
    String authority = role.getAuthority();

    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(simpleGrantedAuthority);

    return authorities;
  }

  @Override
  public String getUsername() {
    return this.adminName;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}