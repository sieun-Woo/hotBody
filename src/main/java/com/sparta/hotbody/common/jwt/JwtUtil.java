package com.sparta.hotbody.common.jwt;

import com.sparta.hotbody.admin.entity.Admin;
import com.sparta.hotbody.admin.repository.AdminRepository;
import com.sparta.hotbody.admin.service.AdminDetailsServiceImpl;
import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import com.sparta.hotbody.common.jwt.repository.RefreshTokenRedisRepository;
import com.sparta.hotbody.user.entity.User;
import com.sparta.hotbody.user.entity.UserRole;
import com.sparta.hotbody.user.repository.UserRepository;
import com.sparta.hotbody.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {


  private final UserRepository userRepository;
  private final AdminRepository adminRepository;
  private final RefreshTokenRedisRepository refreshTokenRedisRepository;
  private final UserDetailsServiceImpl userDetailsService;
  private final AdminDetailsServiceImpl adminDetailsService;

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String REFRESH_TOKEN = "RefreshToken";
  public static final String AUTHORIZATION_KEY = "auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 1??????
  private static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 2???

  @Value("${jwt.secret.key}")
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  // Header ????????? ????????? ????????????
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }
  // Header ???????????? ????????? ????????????
  public String resolveRefreshToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(REFRESH_TOKEN);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  // Cookie ???????????? ????????? ????????????
  public String resolveRefreshTokenFromCookie(HttpServletRequest request) {
    return getToken(request, REFRESH_TOKEN);
  }

  private String getToken(HttpServletRequest request, String Token) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (Optional.of(cookie).isPresent()) {
        if (cookie.getName().equals(Token)) {
          String bearerToken = URLDecoder.decode(cookie.getValue());
          if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
          }
        }
      }
    }
    return null;
  }

  // ????????? ?????? ??????
  public String createAccessToken(String username, UserRole role) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  public String reCreateAccessToken(String token) {
    Claims claims = getUserInfoFromToken(token);
    System.out.println(claims);
    String role = claims.get(AUTHORIZATION_KEY).toString();

    switch (role) {
      case ("USER"):
        return reCreateUserAccessToken(token);

      case ("ADMIN"):
        return reCreateAdminAccessToken(token);
    }
    return null;
  }

  // ?????? ????????? ?????? ?????????
  public String reCreateUserAccessToken(String token) {
    Date date = new Date();
    Claims claims = getUserInfoFromToken(token);
    String username = claims.getSubject();
    User user = userRepository.findByUsername(username).get();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(user.getUsername())
            .claim(AUTHORIZATION_KEY, user.getRole())
            .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  // ????????? ????????? ?????? ?????????
  public String reCreateAdminAccessToken(String token) {
    Date date = new Date();
    Claims claims = getUserInfoFromToken(token);
    String username = claims.getSubject();
    Admin admin = adminRepository.findByUsername(username).get();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(admin.getUsername())
            .claim(AUTHORIZATION_KEY, admin.getRole())
            .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  // ???????????? ?????? ??????
  public String createRefreshToken(String username, UserRole role) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  public boolean validateToken(String token)
      throws ExpiredJwtException {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature, ???????????? ?????? JWT ?????? ?????????.");

    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token, ????????? JWT token ?????????.");
      throw e;

    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token, ???????????? ?????? JWT ?????? ?????????.");

    } catch (IllegalArgumentException e) {
      log.info("JWT claims is empty, ????????? JWT ?????? ?????????.");

    }
    return false;
  }

  public boolean validateRefreshToken(String token) {
    if (refreshTokenRedisRepository.findByRefreshToken(BEARER_PREFIX + token).isPresent()) {
      try {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return true;
      } catch (SecurityException | MalformedJwtException e) {
        log.info("Invalid JWT signature, ???????????? ?????? JWT ?????? ?????????.");

      } catch (ExpiredJwtException e) {
        log.info("Expired JWT token, ????????? JWT token ?????????.");

      } catch (UnsupportedJwtException e) {
        log.info("Unsupported JWT token, ???????????? ?????? JWT ?????? ?????????.");

      } catch (IllegalArgumentException e) {
        log.info("JWT claims is empty, ????????? JWT ?????? ?????????.");
      }
    }
    return false;
  }

  //????????????
  public boolean logout(HttpServletRequest request) {
    String token = resolveRefreshToken(request);
    if (token != null) {
      RefreshToken refreshToken = refreshTokenRedisRepository.findByRefreshToken(BEARER_PREFIX + token).get();
      log.info(refreshToken.toString());
      refreshTokenRedisRepository.delete(refreshToken);
      return true;
    }
    return false;
  }

  // ?????? ????????? ??????
  public boolean validate(HttpServletRequest request) {
    String token = resolveRefreshToken(request);
    if (token != null && refreshTokenRedisRepository.findByRefreshToken(BEARER_PREFIX + token).isPresent()) {
      return false;
    } else {
      return true;
    }
  }

  // ????????? ???????????? ?????? ?????????
  public String urlEncoder(String token) throws UnsupportedEncodingException {
    return URLEncoder.encode(token, "utf-8");
  }

  // ?????? ?????? ?????? ??????
  public Authentication createAuthentication(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  // ????????? ?????? ?????? ??????
  public Authentication createAdminAuthentication(String username) {
    UserDetails userDetails = adminDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  // ???????????? ????????? ?????? ????????????
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
}
