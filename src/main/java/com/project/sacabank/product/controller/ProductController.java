package com.project.sacabank.product.controller;

import static com.project.sacabank.utils.Constants.API_PRODUCT_PATH;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
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
import com.project.sacabank.product.dto.ProductDto;
import com.project.sacabank.product.service.ProductService;
import com.project.sacabank.repositories.CategoryRepository;
import com.project.sacabank.user.model.User;

@RestController
@RequestMapping(path = API_PRODUCT_PATH)
public class ProductController extends BaseController {

  @Autowired
  ProductService service;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ModelMapper mapper;

  @GetMapping("public")
  public ResponseEntity<?> getAllProduct(
      @RequestParam Optional<String> search,
      @RequestParam Optional<Integer> page,
      @RequestParam Optional<Integer> limit,
      @RequestParam Optional<UUID> category_id,
      @RequestParam Optional<UUID> user_id,
      @RequestParam Optional<Boolean> isNullQuantity) {
    return this.onSuccess(service.getAll(search, page, limit, category_id, user_id, isNullQuantity));
  }

  @GetMapping("public/{slug}")
  public ResponseEntity<?> getAllProduct(@PathVariable String slug) {
    return this.onSuccess(service.findBySlug(slug));
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getAllProduct(@PathVariable("id") UUID id) {
    return this.onSuccess(service.findOne(id));
  }

  @GetMapping("my_product")
  public ResponseEntity<?> getMyProduct(
      @RequestParam Optional<String> search,
      @RequestParam Optional<Integer> page,
      @RequestParam Optional<Integer> limit) {

    User user = this.getUserInfo();
    return this.onSuccess(service.getByUserId(user, search, page, limit));
  }

  @PostMapping("")
  public ResponseEntity<?> create(@RequestBody ProductDto productDto) {
    return this.onSuccess(service.create(this.getUserInfo(), productDto));
  }

  @PatchMapping("{id}")
  public ResponseEntity<?> create(@PathVariable("id") UUID id, @RequestBody ProductDto productDto) {
    return this.onSuccess(service.update(id, productDto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
    return this.onSuccess(service.remove(id));
  }

  @GetMapping("public/list")
  public ResponseEntity<?> getListProduct(@RequestParam List<UUID> ids) {
    return this.onSuccess(service.findInIds(ids));
  }

}
