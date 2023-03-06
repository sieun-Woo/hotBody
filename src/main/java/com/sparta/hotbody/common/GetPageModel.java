package com.sparta.hotbody.common;

import lombok.Data;

@Data
public class GetPageModel {

  private int page;
  private int size;
  private String sortBy;
  private boolean isAsc;

}
