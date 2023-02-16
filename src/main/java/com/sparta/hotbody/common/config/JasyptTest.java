package com.sparta.hotbody.common.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
  public static void main(String[] args) {
    StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
    jasypt.setPassword("IOuDpeuDpe2OgOy5mDYzNTI=");      //암호화 키(password)
    jasypt.setAlgorithm("PBEWithMD5AndDES");


    String encryptedText = jasypt.encrypt("jC+nUO0nYwT9GMbgpGgpJXkErRuFo/paoCPV9m1L");    //암호화
    String plainText = jasypt.decrypt(encryptedText);  //복호화

    System.out.println("encryptedText:  " + encryptedText); //암호화된 값
    System.out.println("plainText:  " + plainText);         //복호화된 값
  }
}

