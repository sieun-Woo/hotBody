package com.sparta.hotbody.common.batch.aquaticProducts;



import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AquaticProductsCsvWriter implements ItemWriter<AquaticProducts> {

  private final AquaticProductsRepository aquaticProductsRepository;
  @Override
  public void write(List<? extends AquaticProducts> list) throws Exception {
    aquaticProductsRepository.saveAll(new ArrayList<AquaticProducts>(list));
  }
}
