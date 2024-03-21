package com.project.sacabank.upload;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.sacabank.googleDriver.GoogleDriverService;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
  @Autowired
  private GoogleDriverService service;

  @GetMapping()
  public String getAllAudio() throws IOException, GeneralSecurityException {
    // return service.getfiles();
    return "";
  }

  @GetMapping("/callback")
  public String callback() {

    return "";
  }

  @PostMapping()
  public String uploadFile(MultipartFile file) throws IOException, GeneralSecurityException {
    System.out.println(file.getOriginalFilename());

    return service.uploadFile(file);
  }
}
