package com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgriculturalAndLivestockProductsRepository extends JpaRepository<AgriculturalAndLivestockProducts, Long> {
  List<AgriculturalAndLivestockProducts> findAllByFoodNameContaining(String foodName);
}
