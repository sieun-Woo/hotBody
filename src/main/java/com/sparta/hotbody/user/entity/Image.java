package com.sparta.hotbody.user.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Image_ID")
  private Long id;
  @Column(nullable = false)
  private String mimetype;
  @Column(nullable = false)
  private String original_name;
  @Column(nullable = false)
  private byte[] data;
  @Column(nullable = false)
  private String created;
}