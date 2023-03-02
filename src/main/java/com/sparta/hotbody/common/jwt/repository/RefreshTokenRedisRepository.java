package com.sparta.hotbody.common.jwt.repository;

import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByRefreshToken(String token);
}
