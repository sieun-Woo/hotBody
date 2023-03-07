package com.sparta.hotbody.upload.controller;

import com.sparta.hotbody.upload.entity.Image;
import com.sparta.hotbody.upload.repository.ImageRepository;
import com.sparta.hotbody.upload.service.UploadService;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UploadController {

  private final ImageRepository imageRepository;
  private final UploadService uploadService;

//  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TRAINER')")
//  @GetMapping("/upload")
//  public String newFile() {
//    return "upload-form";
//  }

  @GetMapping("/image")
  public String putImage(@RequestPart MultipartFile multipartFile) {
    return uploadService.putImage(multipartFile);
  }

//  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TRAINER')")
//  @PostMapping("/upload")
//  public String saveFile(
//          @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
//
//    Image image = uploadService.storeFile(file);
//    redirectAttributes.addAttribute("itemId", image.getId());
//
//    return "redirect:/api/upload/{itemId}";
//  }

//  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TRAINER')")
//  @GetMapping("/upload/{id}")
//  public String items(@PathVariable Long id, Model model) {
//    Image image = imageRepository.findById(id).get();
//    model.addAttribute("item", image);
//    return "item-view";
//  }

//  // 이미지 조회
//  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TRAINER')")
//  @ResponseBody
//  @GetMapping("/image/{filename}")
//  public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
//    return uploadService.viewImage(filename);
//  }
//}

}
