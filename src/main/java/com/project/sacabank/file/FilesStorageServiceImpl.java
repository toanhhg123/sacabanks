package com.project.sacabank.file;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.sacabank.exception.CustomException;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new CustomException("Could not initialize folder for upload!");
    }
  }

  @Override
  public String save(MultipartFile file) {
    try {
      String fileExtension = file.getOriginalFilename();
      String uuidFileName = UUID.randomUUID().toString() + fileExtension;
      Files.copy(file.getInputStream(), this.root.resolve(uuidFileName));
      return uuidFileName;

    } catch (FileAlreadyExistsException e) {
      throw new CustomException("A file of that name already exists.");
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new CustomException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new CustomException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new CustomException("Could not load the files!");
    }
  }

  @Override
  public String getImageContentType(String extension) {
    switch (extension) {
      case "png":
        return "image/png";
      case "jpg":
      case "jpeg":
        return "image/jpeg";
      case "gif":
        return "image/gif";
      default:
        return null;
    }

  }

  private Dimension parseSize(String size) {
    try {
      String[] dimensions = size.split("x");
      int width = Integer.parseInt(dimensions[0]);
      int height = Integer.parseInt(dimensions[1]);
      return new Dimension(width, height);
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid size format. Expected format: WIDTHxHEIGHT, e.g., 250x250");
    }
  }

  @Override
  public ImageResizeResource getResizedImage(String size, String filename) throws IOException {
    Path filePath = root.resolve(filename);
    Dimension sizeImage = parseSize(size);

    // Check if the file exists and is an image
    if (!Files.exists(filePath)) {
      throw new CustomException("Không tìm thấy hình ảnh");
    }

    // Determine the file extension for content type
    String fileExtension = StringUtils.getFilenameExtension(filename);
    String contentType = getImageContentType(fileExtension);

    if (contentType == null) {
      throw new CustomException("hình ảnh không hợp lệ");
    }

    BufferedImage originalImage = ImageIO.read(filePath.toFile());

    // Resize the image to 250x250 pixels
    BufferedImage resizedImage = resizeImage(originalImage, sizeImage.width(), sizeImage.height());

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(resizedImage, fileExtension, outputStream);

    return new ImageResizeResource(
        new ByteArrayResource(outputStream.toByteArray()),
        contentType);

  }

  private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
    Image resultingImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = outputImage.createGraphics();
    g2d.drawImage(resultingImage, 0, 0, null);
    g2d.dispose();

    return outputImage;
  }
}
