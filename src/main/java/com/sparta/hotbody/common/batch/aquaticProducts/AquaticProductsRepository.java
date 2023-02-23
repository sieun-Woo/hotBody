package com.sparta.hotbody.common.batch.aquaticProducts;


import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProducts;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AquaticProductsRepository extends JpaRepository<AquaticProducts, Long> {
  List<AquaticProducts> findAllByFoodNameContaining(String foodName);

  Page<AquaticProducts> findAllByFoodNameContaining(String foodName, Pageable pageable);



}
