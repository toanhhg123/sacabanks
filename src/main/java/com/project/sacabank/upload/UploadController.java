package com.project.sacabank.upload;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.file.FileInfo;
import com.project.sacabank.file.FilesStorageService;

@RestController
@RequestMapping("/api/upload")
public class UploadController extends BaseController {

  @Autowired
  FilesStorageService storageService;

  @GetMapping()
  public String getAllAudio() throws IOException, GeneralSecurityException {
    // return service.getfiles();
    return "";
  }

  @GetMapping("/callback")
  public String callback() {

    return "";
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(UploadController.class, "getListFiles", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) {
    return this.onSuccess(storageService.save(file));
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
}
