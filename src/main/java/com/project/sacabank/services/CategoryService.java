package com.project.sacabank.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.units.qual.s;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.base.FullRepo;
import com.project.sacabank.category.CategorySpecifications;
import com.project.sacabank.category.dto.CategoryDto;
import com.project.sacabank.category.dto.CategoryWithCountProduct;
import com.project.sacabank.category.model.Category;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.repositories.CategoryRepository;

@Service
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
  public List<CategoryWithCountProduct> gets(Optional<String> name) {

    String nativeQuery = "SELECT c.*, COUNT(cp.id) as product_quantity " +
        "FROM category c " +
        "LEFT JOIN category_product cp ON c.id = cp.category_id " +
        "GROUP BY c.id";

    return fullRepo.entityManager.createNativeQuery(nativeQuery, CategoryWithCountProduct.class).getResultList();

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
