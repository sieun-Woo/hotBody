package com.sparta.hotbody.common.page;

import javax.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageDto {
  @Positive
  private Integer currentPage;
//  private Integer size;
//  private String sortBy;

  public Pageable toPageable() {
    return PageRequest.of(currentPage-1, 5, Sort.by("id").descending());
  }
}
