package com.project.sacabank.productCategory;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseService;
import com.project.sacabank.productCategory.dto.ProductCategoryCreate;
import com.project.sacabank.services.CategoryService;

@Service
public class ProductCategoryService extends BaseService<ProductCategoryModel> {

  @Autowired
  CategoryService categoryService;

  public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
    super.InJectRepository(productCategoryRepository);
  }

  @Transactional
  @Override
  public ProductCategoryModel create(BaseDto dto) {

    ProductCategoryCreate categoryCreate = (ProductCategoryCreate) dto;

    List<UUID> categoriesId = categoryService.findFullCategoriesParentId(categoryCreate.getCategoryId());

    List<ProductCategoryModel> productCategoryModels = categoriesId.stream().map(p -> ProductCategoryModel.builder()
        .categoryId(p)
        .productId(categoryCreate.getProductId())
        .build()).toList();

    var data = this.nBaseRepository.saveAll(productCategoryModels);

    // ! check transaction
    categoryService.findFullCategoriesParentId(categoryCreate.getCategoryId());

    return data.get(0);
  }

}
