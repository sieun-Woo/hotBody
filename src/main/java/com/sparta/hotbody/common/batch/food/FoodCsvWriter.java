package com.sparta.hotbody.common.batch.food;



import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FoodCsvWriter implements ItemWriter<Food> {

  private final FoodRepositroy foodRepositroy;
  @Override
  public void write(List<? extends Food> list) throws Exception {
    foodRepositroy.saveAll(new ArrayList<Food>(list));
  }
}
