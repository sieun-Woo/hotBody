package com.sparta.hotbody.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.hotbody.common.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = jwtUtil.resolveToken(request);
    String refreshToken = jwtUtil.resolveRefreshToken(request);
    try {
      if (token != null) {
        if (!jwtUtil.validateToken(token, response)) {

          jwtExceptionHandler(response, "Invalid JWT signature", HttpStatus.BAD_REQUEST.value());
          return;
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);
        setAuthentication(info.getSubject(), info.get(jwtUtil.AUTHORIZATION_KEY).toString());
      }
    } catch (ExpiredJwtException e) {
      if (refreshToken == null) {

        jwtExceptionHandler(response, "Expired JWT token, 만료된 JWT token 입니다.",
            HttpStatus.BAD_REQUEST.value());
        return;

      }
      if (jwtUtil.validateRefreshToken(refreshToken)) {
        String reCreateAccessToken = jwtUtil.reCreateAccessToken(refreshToken);
        Claims info = jwtUtil.getUserInfoFromToken(reCreateAccessToken.substring(7));
        response.setHeader(jwtUtil.AUTHORIZATION_HEADER, reCreateAccessToken);
        setAuthentication(info.getSubject(), info.get(jwtUtil.AUTHORIZATION_KEY).toString());
        filterChain.doFilter(request, response);
      }
    }
    filterChain.doFilter(request, response);
  }

  public void setAuthentication(String username, String role) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    switch (role) {
      case "USER":
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        break;

      case "ADMIN":
        Authentication adminAuthentication = jwtUtil.createAdminAuthentication(username);
        context.setAuthentication(adminAuthentication);
        SecurityContextHolder.setContext(context);
        break;
    }
  }

  //예외가 발생했을때 사용되는 메소드
  public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
    response.setStatus(statusCode);
    response.setContentType("application/json; charset=utf-8");
    try {
      String json = new ObjectMapper().writeValueAsString(
          new SecurityExceptionDto(statusCode, msg));
      response.getWriter().write(json);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}