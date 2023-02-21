package com.sparta.hotbody.common.batch.processedfood;



import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProcessedFoodCsvWriter implements ItemWriter<ProcessedFood> {

  private final ProcessedFoodRepository processedFoodRepository;
  @Override
  public void write(List<? extends ProcessedFood> list) throws Exception {
    processedFoodRepository.saveAll(new ArrayList<ProcessedFood>(list));
  }
}
