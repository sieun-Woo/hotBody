package com.sparta.hotbody.upload.repository;

import com.sparta.hotbody.upload.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
