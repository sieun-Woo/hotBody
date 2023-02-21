package com.sparta.hotbody.common.config;


import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProducts;
import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProductsCsvReader;
import com.sparta.hotbody.common.batch.agriculturalAndLivestockProducts.AgriculturalAndLivestockProductsCsvWriter;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProducts;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProductsCsvReader;
import com.sparta.hotbody.common.batch.aquaticProducts.AquaticProductsCsvWriter;
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
  private final AgriculturalAndLivestockProductsCsvReader agriculturalAndLivestockProductsCsvReader;
  private final AgriculturalAndLivestockProductsCsvWriter agriculturalAndLivestockProductsCsvWriter;
  private final AquaticProductsCsvReader aquaticProductsCsvReader;
  private final AquaticProductsCsvWriter aquaticProductsCsvWriter;
  private final ProcessedFoodCsvReader processedFoodCsvReader;
  private final ProcessedFoodCsvWriter processedFoodCsvWriter;
  private static final int chunkSize = 1000;

  @Bean
  public Job csvFileItemReaderJob() {
    return jobBuilderFactory.get("csvFileItemReaderJob")
        .start(agriculturalAndLivestockProductsCsvFileItemReaderStep())
        .next(aquaticProductsCsvFileItemReaderStep())
        .next(processedFoodCsvFileItemReaderStep())
        .build();
  }


  @Bean
  public Step agriculturalAndLivestockProductsCsvFileItemReaderStep() {
    return stepBuilderFactory.get("agriculturalAndLivestockProductsCsvFileItemReaderStep")
        .<AgriculturalAndLivestockProducts, AgriculturalAndLivestockProducts>chunk(chunkSize)
        .reader(agriculturalAndLivestockProductsCsvReader.agriculturalAndLivestockProductsCsvFileItemReader())
        .writer(agriculturalAndLivestockProductsCsvWriter)
        .build();

  }

  @Bean
  public Step aquaticProductsCsvFileItemReaderStep() {
    return stepBuilderFactory.get("aquaticProductsCsvFileItemReaderStep")
        .<AquaticProducts, AquaticProducts>chunk(chunkSize)
        .reader(aquaticProductsCsvReader.aquaticProductsCsvFileItemReader())
        .writer(aquaticProductsCsvWriter)
        .build();

  }

  @Bean
  public Step processedFoodCsvFileItemReaderStep() {
    return stepBuilderFactory.get("processedFoodCsvFileItemReaderStep")
        .<ProcessedFood, ProcessedFood>chunk(chunkSize)
        .reader(processedFoodCsvReader.processedFoodCsvFileItemReader())
        .writer(processedFoodCsvWriter)
        .build();

  }


}
