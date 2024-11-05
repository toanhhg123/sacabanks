package com.project.sacabank.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
  public void init();

  public String save(MultipartFile file);

  public Resource load(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();

  public String getImageContentType(String extension);

  public ImageResizeResource getResizedImage(String size, String filename) throws IOException;

}