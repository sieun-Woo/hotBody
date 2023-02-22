package com.sparta.hotbody.common.batch.aquaticProducts;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AquaticProductsRepository extends JpaRepository<AquaticProducts, Long> {
  List<AquaticProducts> findAllByFoodNameContaining(String foodName);
}
