package com.sparta.hotbody.common.config;

import com.sparta.hotbody.common.exception.CustomAccessDeniedHandler;
import com.sparta.hotbody.common.exception.CustomAuthenticationEntryPoint;
import com.sparta.hotbody.common.jwt.JwtAuthFilter;
import com.sparta.hotbody.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(prePostEnabled = true) // @Secured 어노테이션 활성화
@EnableScheduling // @Scheduled 어노테이션 활성화
public class WebSecurityConfig {

  private final JwtUtil jwtUtil;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 가장 먼저 시큐리티를 사용하기 위해선 선언해준다.
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // h2-console 사용 및 resources 접근 허용 설정
    return (web) -> web.ignoring()
        .requestMatchers(PathRequest.toH2Console())
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
        .antMatchers("/api/user/sign-up").permitAll()
        .antMatchers("/api/user/log-in").permitAll()
        .antMatchers("/api/admin/sign-up").permitAll()
        .antMatchers("/api/admin/log-in").permitAll()
        .antMatchers("/api/user/auth/**").hasAnyAuthority("ROLE_USER", "ROLE_TRAINER", "ROLE_ADMIN")
        .antMatchers("/h2-console").permitAll()
        .antMatchers("/api/posts/**").permitAll()
        .antMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
        .antMatchers("/api/").hasAnyAuthority("ROLE_TRAINER")
        .anyRequest().authenticated()
        .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
    http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    return http.build();
  }
}