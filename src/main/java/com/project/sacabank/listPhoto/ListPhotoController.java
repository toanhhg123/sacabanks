package com.project.sacabank.listPhoto;

import static com.project.sacabank.utils.Constants.API_LIST_PHOTO;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.listPhoto.dto.ListPhotoDto;

@RestController
@RequestMapping(path = API_LIST_PHOTO)
public class ListPhotoController extends BaseController {

  @Autowired
  ListPhotoService listPhotoService;

  @PostMapping("")
  public ResponseEntity<?> create(@RequestBody ListPhotoDto listPhotoDto) {
    return this.onSuccess(listPhotoService.create(listPhotoDto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> create(@PathVariable UUID id) {
    return this.onSuccess(listPhotoService.delete(id));
  }

}
