package com.project.sacabank.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
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
import com.project.sacabank.productCategory.ProductCategoryRepository;
import com.project.sacabank.productCategory.ProductCategoryService;
import com.project.sacabank.productCategory.dto.ProductCategoryCreate;
import com.project.sacabank.repositories.CategoryRepository;
import com.project.sacabank.user.model.User;
import com.project.sacabank.utils.Constants.ErrorMessage;
import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository repository;
  private final ListPhotoRepository listPhotoRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final CategoryRepository categoryRepository;
  private final ProductCategoryService productCategoryService;
  private final ModelMapper mapper;

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
      spec = spec.and(ProductSpecifications.belongsToCategory(category_id.get()));
    }

    if (isNullQuantity.isPresent()) {
      spec = Boolean.TRUE.equals(isNullQuantity.get()) ? spec.and(ProductSpecifications.isNullQuantity())
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
    var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
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

  @Transactional
  public Product create(User user, ProductDto productDto) {
    Product product = mapper.map(productDto, Product.class);
    product.setUser(user);

    if (productDto.getCategoryId() != null) {
      var category = categoryRepository.findById(productDto.getCategoryId());
      product.setCategory(category.get());
    }

    product = repository.save(product);

    if (product.getCategory() != null) {
      ProductCategoryCreate pCategoryCreate = ProductCategoryCreate.builder()
          .categoryId(product.getCategory().getId())
          .productId(product.getId())
          .build();

      productCategoryService.create(pCategoryCreate);
    }

    return product;
  }

  public Product findOne(UUID id) {
    var product = repository.findById(id);

    if (!product.isPresent()) {
      throw new CustomException(ErrorMessage.NOT_FOUND_PRODUCT);
    }

    return product.get();
  }

  public Product findBySlug(String slug) {
    Optional<Product> product = repository.findBySlug(slug);

    if (!product.isPresent()) {
      throw new CustomException(ErrorMessage.NOT_FOUND_PRODUCT);
    }

    return product.get();
  }

  @Transactional
  public Product update(UUID id, ProductDto productDto) {
    var product = repository.findById(id);

    if (product.isEmpty()) {
      throw new CustomException(ErrorMessage.NOT_FOUND_PRODUCT);
    }

    if (productDto.getSlug() != null && repository.existsBySlug(productDto.getSlug(), id)) {
      throw new CustomException("slug is exist");
    }

    product.get().updateFromDTO(productDto);

    if (productDto.getCategoryId() != null) {
      var category = categoryRepository.findById(productDto.getCategoryId()).get();
      product.get().setCategory(category);
      productCategoryRepository.deleteByProductId(id);
      ProductCategoryCreate pCategoryCreate = ProductCategoryCreate.builder()
          .categoryId(productDto.getCategoryId())
          .productId(id)
          .build();

      productCategoryService.create(pCategoryCreate);
    }

    repository.save(product.get());

    return product.get();

  }

  @Transactional
  public Product remove(UUID id) {
    var product = repository.findById(id);

    if (!product.isPresent() || product.get() == null) {
      throw new CustomException(ErrorMessage.NOT_FOUND_PRODUCT);
    }

    listPhotoRepository.deleteByProductId(id);
    productCategoryRepository.deleteByProductId(id);
    repository.delete(product.get());
    return product.get();

  }

  public List<Product> findInIds(List<UUID> ids) {
    return repository.findInIds(ids);
  }
}
