package com.sparta.hotbody.common.config;


import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProducts;
import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProductsCsvReader;
import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProductsCsvWriter;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFood;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFoodCsvReader;
import com.sparta.hotbody.common.batch.processedfood.ProcessedFoodCsvWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final ProcessedFoodCsvReader processedFoodCsvReader;
  private final ProcessedFoodCsvWriter processedFoodCsvWriter;
  private final AgriculturalAndLivestockProductsCsvReader agriculturalAndLivestockProductsCsvReader;
  private final AgriculturalAndLivestockProductsCsvWriter agriculturalAndLivestockProductsCsvWriter;
  private static final int chunkSize = 1000;

  @Bean
  public Job csvFileItemReaderJob() {
    return jobBuilderFactory.get("csvFileItemReaderJob")
        .start(agriculturalAndLivestockProductsCsvFileItemReaderStep())
        .build();
  }


  @Bean
  public Step agriculturalAndLivestockProductsCsvFileItemReaderStep() {
    return stepBuilderFactory.get("agriculturalAndLivestockProductsCsvFileItemReaderStep")
        .<AgriculturalAndLivestockProducts, AgriculturalAndLivestockProducts>chunk(chunkSize)
        .reader(agriculturalAndLivestockProductsCsvReader.csvFileItemReader())
        .writer(agriculturalAndLivestockProductsCsvWriter)
        .build();

  }

  public Step processedFoodCsvFileItemReaderStep() {
    return stepBuilderFactory.get("processedFoodCsvFileItemReaderStep")
        .<ProcessedFood, ProcessedFood>chunk(chunkSize)
        .reader(processedFoodCsvReader.csvFileItemReader())
        .writer(processedFoodCsvWriter)
        .build();

  }


}
