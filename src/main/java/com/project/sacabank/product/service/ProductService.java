package com.project.sacabank.product.service;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.exception.CustomException;
import com.project.sacabank.product.ProductSpecifications;
import com.project.sacabank.product.dto.ProductDto;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.user.model.User;

@Service
@SuppressWarnings("null")
public class ProductService {
  @Autowired
  ProductRepository repository;

  @Autowired
  ModelMapper mapper;

  public List<?> getAll(Optional<String> search, Optional<Integer> page, Optional<UUID> category_id) {
    Specification<Product> spec = Specification.where(null);
    var pageNumber = page.isPresent() ? page.get() : 0;

    if (search.isPresent()) {
      spec = spec.or(ProductSpecifications.titleIsLike(search.get()));
      spec = spec.or(ProductSpecifications.itemNumberIsLike(search.get()));
    }

    if (!category_id.isEmpty()) {
      spec = spec.and(ProductSpecifications.isEqualCategoryId(category_id.get()));
    }

    return repository.findAll(spec, PageRequest.of(pageNumber, PAGE_SIZE));

  }

  public List<?> getByUserId(User user, Optional<String> search, Optional<Integer> page) {
    Specification<Product> spec = Specification.where(null);
    var pageNumber = page.isPresent() ? page.get() : 0;
    if (!search.isEmpty()) {
      spec = spec.or(ProductSpecifications.titleIsLike(search.get()));
      spec = spec.or(ProductSpecifications.itemNumberIsLike(search.get()));
    }

    spec = spec.and(ProductSpecifications.isEqualUser(user));
    return repository.findAll(spec, PageRequest.of(pageNumber, PAGE_SIZE));

  }

  public Product create(Product product) {
    return repository.save(product);
  }

  public Product findOne(UUID id) {
    var product = repository.findById(id);

    if (!product.isPresent() || product.get() == null) {
      throw new CustomException("not find product");
    }

    return product.get();
  }

  public Product findBySlug(String slug) {
    var product = repository.findBySlug(slug);

    if (!product.isPresent() || product.get() == null) {
      throw new CustomException("not find product");
    }

    return product.get();
  }

  @Transactional
  public Product update(UUID id, ProductDto productDto) {
    var product = repository.findById(id);

    if (product.isEmpty()) {
      throw new CustomException("not found product");
    }

    if (productDto.getSlug() != null && !repository.existsBySlug(productDto.getSlug(), id)) {
      throw new CustomException("slog is exist");
    }

    product.get().updateFromDTO(productDto);
    return repository.save(product.get());

  }

  @Transactional
  public Product remove(UUID id) {
    var product = repository.findById(id);

    if (!product.isPresent() || product.get() == null) {
      throw new CustomException("not find product");
    }

    repository.delete(product.get());
    return product.get();

  }
}
