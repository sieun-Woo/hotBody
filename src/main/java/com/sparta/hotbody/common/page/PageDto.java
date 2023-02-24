package com.sparta.hotbody.common.page;

import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
public class PageDto {
  @Positive
  private int currentPage;
//  private Integer size;
//  private String sortBy;

  public PageDto(int currentPage) {
    this.currentPage = currentPage;
  }

  public Pageable toPageable() {
    return PageRequest.of(currentPage-1, 10, Sort.by("id").descending());
  }
}
