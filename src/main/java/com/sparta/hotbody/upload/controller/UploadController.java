package com.sparta.hotbody.upload.controller;

import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.repository.ImageRepository;
import com.sparta.hotbody.upload.service.UploadService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UploadController {

  private final ImageRepository imageRepository;
  private final UploadService uploadService;

  @GetMapping("/upload")
  public String newFile() {
    return "upload-form";
  }

  @PostMapping("/upload")
  public String saveFile(
      @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

      Image image = uploadService.storeFile(file);
      redirectAttributes.addAttribute("itemId",image.getId());

    return "redirect:/api/upload/{itemId}";
  }
  @GetMapping("/upload/{id}")
  public String items(@PathVariable Long id, Model model) {
    Image image = imageRepository.findById(id).get();
    model.addAttribute("item", image);
    return "item-view";

  }

  // 이미지 조회
  @ResponseBody
  @GetMapping("/image/{filename}")
  public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
    return new UrlResource("file:" + uploadService.getFullPath(filename));
  }
}
