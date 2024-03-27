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

import com.project.sacabank.category.CategorySpecifications;
import com.project.sacabank.category.dto.CategoryDto;
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

  public Category create(CategoryDto categoryCreate) {
    Category category = mapper.map(categoryCreate, Category.class);

    if (category == null)
      throw new CustomException("Not map category");

    return categoryRepository.save(category);
  }

  public List<?> gets(Optional<String> name) {
    Specification<Category> spec = Specification.where(null);

    if (name.isPresent()) {
      spec = spec.and(CategorySpecifications.nameIsLike(name.get()));
    }

    return categoryRepository.findAll(spec);
  }

  public Category update(UUID id, CategoryDto categoryDto) {
    var category = categoryRepository.findById(id);
    if (!category.isPresent()) {
      throw new CustomException("not found category");
    }

    category.get().setName(categoryDto.getName());
    category.get().setImage(categoryDto.getImage());
    category.get().setRank(categoryDto.getRank());

    return categoryRepository.save(category.get());
  }

  public Category findById(UUID id) {
    var category = categoryRepository.findById(id);
    if (!category.isPresent()) {
      throw new CustomException("not found category");
    }
    return category.get();
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
