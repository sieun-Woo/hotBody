package com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts;



import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AgriculturalAndLivestockProductsCsvWriter implements ItemWriter<AgriculturalAndLivestockProducts> {

  private final AgriculturalAndLivestockProductsRepository agriculturalAndLivestockProductsRepository;
  @Override
  public void write(List<? extends AgriculturalAndLivestockProducts> list) throws Exception {
    agriculturalAndLivestockProductsRepository.saveAll(new ArrayList<AgriculturalAndLivestockProducts>(list));
  }
}
