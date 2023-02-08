package com.sparta.hotbody.diet.Conrtroller;

import com.sparta.hotbody.diet.Service.DietServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class DietController {

  private final DietServiceImpl dietServiceImpl;

  @GetMapping("/diet")
  public void readDatabase() {

  }
}
