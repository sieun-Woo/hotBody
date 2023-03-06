package com.sparta.hotbody.common.page;

import com.sparta.hotbody.common.GetPageModel;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
public class PageDto {
  @Positive
  private int page;
  private int size;
  private String sortBy;
  private boolean isAsc;

  public Pageable toPageable(GetPageModel getPageModel) {
    Sort.Direction direction = getPageModel.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, getPageModel.getSortBy());
    Pageable pageable = PageRequest.of(getPageModel.getPage()-1, getPageModel.getSize(), sort);
    return pageable;
  }
}
