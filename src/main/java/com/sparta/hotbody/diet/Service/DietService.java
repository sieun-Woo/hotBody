package com.sparta.hotbody.diet.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DietService {
  public Map<Integer, List<String>> readExcel() throws IOException;


  }
