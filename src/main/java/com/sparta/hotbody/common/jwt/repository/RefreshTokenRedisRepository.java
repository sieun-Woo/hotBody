package com.sparta.hotbody.common.jwt.repository;

import com.sparta.hotbody.common.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {

}
