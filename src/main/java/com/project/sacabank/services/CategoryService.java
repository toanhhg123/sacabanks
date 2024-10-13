package com.project.sacabank.services;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.base.FullRepo;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.category.dto.CategoryDto;
import com.project.sacabank.category.dto.CategoryWithCountProduct;
import com.project.sacabank.category.model.Category;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.repositories.CategoryRepository;

import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ModelMapper mapper;

  @Autowired
  FullRepo fullRepo;

  public List<UUID> findFullCategoriesParentId(UUID id) {
    return categoryRepository.findCategoryHierarchy(id);
  }

  public Category create(CategoryDto categoryCreate) {
    Category category = mapper.map(categoryCreate, Category.class);

    if (category == null)
      throw new CustomException("Not map category");

    return categoryRepository.save(category);
  }

  @SuppressWarnings("unchecked")
  public PaginationResponse gets(Optional<String> search, Optional<Integer> page,
      Optional<Integer> pageSize) {

    var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
    var size = pageSize.isPresent() ? pageSize.get() : PAGE_SIZE;

    Pageable pageable = PageRequest.of(pageNumber, size);

    String countQuery = "SELECT COUNT(DISTINCT c.id) " +
        "FROM category c where c.name like '%" + search.orElse("") + "%'";

    String nativeQuery = "SELECT c.*, COUNT(cp.id) as product_quantity " +
        "FROM category c " +
        "LEFT JOIN category_product cp ON c.id = cp.category_id " +
        "WHERE c.name like '%" + search.orElse("") + "%' " +
        "GROUP BY c.id " +
        "LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

    Query query = fullRepo.entityManager.createNativeQuery(nativeQuery, CategoryWithCountProduct.class);
    List<CategoryWithCountProduct> results = query.getResultList();

    var countQueryObj = (Number) fullRepo.entityManager.createNativeQuery(countQuery).getSingleResult();
    var totalPage = (int) Math.ceil(countQueryObj.doubleValue() / size);

    return PaginationResponse.builder().totalPage(totalPage).count(countQueryObj.intValue()).list(results).build();

  }

  public Category update(UUID id, CategoryDto categoryDto) {
    var category = categoryRepository.findById(id);
    if (!category.isPresent()) {
      throw new CustomException("not found category");
    }

    category.get().setName(categoryDto.getName());
    category.get().setImage(categoryDto.getImage());
    category.get().setRank(categoryDto.getRank());
    category.get().setCategoryId(categoryDto.getCategoryId());

    return categoryRepository.save(category.get());
  }

  public Category findById(UUID id) {
    var category = categoryRepository.findById(id);
    if (!category.isPresent()) {
      throw new CustomException("not found category");
    }
    return category.get();
  }

  public Category addToWhiteList(UUID id) {
    var category = findById(id);
    category.setRank(1);
    return categoryRepository.save(category);
  }

  public Category removeWhiteList(UUID id) {
    var category = findById(id);
    category.setRank(0);
    return categoryRepository.save(category);
  }

  public List<Category> getCategoryWhiteList() {
    return categoryRepository.getWhiteList();
  }

  @Transactional
  public Category remove(UUID id) {
    var category = categoryRepository.findById(id);
    if (!category.isPresent()) {
      throw new CustomException("not found category");
    }
    productRepository.updateCategoryAssociationToNull(id);
    categoryRepository.delete(category.get());
    return category.get();
  }

}
