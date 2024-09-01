package com.project.sacabank.category;

import static com.project.sacabank.utils.Constants.API_CATEGORY_PATH;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.sacabank.base.BaseController;
import com.project.sacabank.category.dto.CategoryDto;
import com.project.sacabank.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = API_CATEGORY_PATH)
public class CategoryController extends BaseController {

  @Autowired
  CategoryService service;

  @PostMapping("")
  public ResponseEntity<?> CreateNew(@Valid @RequestBody CategoryDto categoryDto) {
    return this.onSuccess(service.create(categoryDto));
  }

  @GetMapping("")
  public ResponseEntity<?> get(@RequestParam Optional<String> name) {
    return this.onSuccess(service.gets(name));
  }

  @GetMapping("white_list")
  public ResponseEntity<?> getWhiteList() {
    return this.onSuccess(service.getCategoryWhiteList());
  }

  @PostMapping("white_list/{id}")
  public ResponseEntity<?> getWhiteList(@PathVariable UUID id) {
    return this.onSuccess(service.addToWhiteList(id));
  }

  @DeleteMapping("white_list/{id}")
  public ResponseEntity<?> removeWhiteList(@PathVariable UUID id) {
    return this.onSuccess(service.removeWhiteList(id));
  }

  @GetMapping("parent/{id}")
  public ResponseEntity<?> getFullParent(@PathVariable UUID id) {
    return this.onSuccess(service.findFullCategoriesParentId(id));
  }

  @PatchMapping("{id}")
  public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody CategoryDto categoryDto) {
    return this.onSuccess(service.update(id, categoryDto));
  }

  @GetMapping("{id}")
  public ResponseEntity<?> findOne(@PathVariable UUID id) {
    return this.onSuccess(service.findById(id));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> remove(@PathVariable UUID id) {
    return this.onSuccess(service.remove(id));
  }

}
