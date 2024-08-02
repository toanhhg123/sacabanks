package com.project.sacabank.product.service;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.listPhoto.ListPhotoRepository;
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
  ListPhotoRepository listPhotoRepository;

  @Autowired
  ModelMapper mapper;

  public PaginationResponse getAll(
      Optional<String> search,
      Optional<Integer> page,
      Optional<Integer> pageSize,
      Optional<UUID> category_id,
      Optional<UUID> userId,
      @RequestParam Optional<Boolean> isNullQuantity) {

    Specification<Product> spec = Specification.where(null);
    var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
    var size = pageSize.isPresent() ? pageSize.get() : PAGE_SIZE;

    if (search.isPresent()) {
      spec = spec.or(ProductSpecifications.titleIsLike(search.get()));
      spec = spec.or(ProductSpecifications.itemNumberIsLike(search.get()));
    }

    if (userId.isPresent()) {
      spec = spec.and(ProductSpecifications.isEqualUserId(userId.get()));
    }

    if (!category_id.isEmpty()) {
      spec = spec.and(ProductSpecifications.isEqualCategoryId(category_id.get()));
    }

    if (isNullQuantity.isPresent()) {
      spec = isNullQuantity.get() == true ? spec.and(ProductSpecifications.isNullQuantity())
          : spec.and(ProductSpecifications.isNotNullQuantity());
    }

    var count = repository.count(spec);
    var list = repository.findAll(spec, PageRequest.of(pageNumber, size));
    var totalPage = (int) Math.ceil((double) count / size);

    return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();

  }

  public PaginationResponse getByUserId(User user, Optional<String> search, Optional<Integer> page,
      Optional<Integer> limit) {
    Specification<Product> spec = Specification.where(null);
    var pageNumber = page.isPresent() ? page.get() : 0;
    var size = limit.isPresent() ? limit.get() : PAGE_SIZE;

    if (!search.isEmpty()) {
      spec = spec.or(ProductSpecifications.titleIsLike(search.get()));
      spec = spec.or(ProductSpecifications.itemNumberIsLike(search.get()));
    }

    spec = spec.and(ProductSpecifications.isEqualUser(user));
    Integer count = repository.count(spec);
    Integer totalPage = (int) Math.ceil((double) count / size);
    var list = repository.findAll(spec, PageRequest.of(pageNumber, size));
    return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();

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

    if (productDto.getSlug() != null && repository.existsBySlug(productDto.getSlug(), id)) {
      throw new CustomException("slug is exist");
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

    listPhotoRepository.deleteByProductId(id);
    repository.delete(product.get());
    return product.get();

  }
}
