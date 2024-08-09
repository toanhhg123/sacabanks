package com.project.sacabank.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.productCategory.ProductCategoryRepository;

@Component
public class FullRepo {
  @Autowired
  public ProductRepository productRepository;

  @Autowired
  public ProductCategoryRepository productCategoryRepository;
}
