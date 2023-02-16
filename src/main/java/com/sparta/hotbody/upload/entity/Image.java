package com.sparta.hotbody.upload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String uploadFileName;
  @Column
  private String storeFileName;
  @Column
  private String filePath;
  @Column
  private String resourcePath;

  @Builder
  public Image(String uploadFileName, String storeFileName, String resourcePath, String filePath) {
    this.uploadFileName = uploadFileName;
    this.storeFileName = storeFileName;
    this.resourcePath = resourcePath;
    this.filePath = filePath;
  }
}
