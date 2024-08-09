package com.project.sacabank.productCategory;

import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseService;

@Service
public class ProductCategoryService extends BaseService<ProductCategoryModel> {

  public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
    super.InJectRepository(productCategoryRepository);
  }

}
