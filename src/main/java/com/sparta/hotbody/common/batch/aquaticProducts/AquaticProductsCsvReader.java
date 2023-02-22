package com.sparta.hotbody.common.batch.aquaticProducts;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class AquaticProductsCsvReader {
  @Bean
  @StepScope
  public FlatFileItemReader<AquaticProducts> aquaticProductsCsvFileItemReader() {
    /* file read */
    FlatFileItemReader<AquaticProducts> flatFileItemReader = new FlatFileItemReader<>();
    flatFileItemReader.setResource(new ClassPathResource(
        "foodData/aquaticProducts.csv"));
    flatFileItemReader.setLinesToSkip(1); // header line skip
    flatFileItemReader.setEncoding("UTF-8"); // encoding

    /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
    DefaultLineMapper<AquaticProducts> defaultLineMapper = new DefaultLineMapper<>();

    /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
    DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
    delimitedLineTokenizer.setNames("foodName", "OneTimeSupply", "energy", "protein", "fat", "carbohydrate");
    defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

    /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
    BeanWrapperFieldSetMapper<AquaticProducts> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
    beanWrapperFieldSetMapper.setTargetType(AquaticProducts.class);

    defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

    /* lineMapper 지정 */
    flatFileItemReader.setLineMapper(defaultLineMapper);

    return flatFileItemReader;
  }
}
