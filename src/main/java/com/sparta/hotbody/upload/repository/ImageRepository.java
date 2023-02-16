package com.sparta.hotbody.upload.repository;

import com.sparta.hotbody.upload.entity.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
  Optional<Image> findByResourcePath(String resourcePath);
}
