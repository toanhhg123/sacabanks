package com.project.sacabank.blog;

import static com.project.sacabank.utils.Constants.BLOG_API;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.blog.dto.BlogDto;

@RestController
@RequestMapping(path = BLOG_API)
public class BlogController extends BaseController {

  @Autowired
  BlogService service;

  @GetMapping("")
  public ResponseEntity<?> getAll() {
    return this.onSuccess(service.getAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<?> findOne(@PathVariable UUID id) {
    return this.onSuccess(service.getById(id));
  }

  @PostMapping("")
  public ResponseEntity<?> create(@RequestBody BlogDto body) {
    return this.onSuccess(service.create(body));
  }

  @PatchMapping("{id}")
  public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody BlogDto body) {
    return this.onSuccess(service.update(id, body));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> remove(@PathVariable UUID id) {
    return this.onSuccess(service.removeById(id));
  }

}
