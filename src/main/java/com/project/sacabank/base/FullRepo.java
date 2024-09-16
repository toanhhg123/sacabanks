package com.project.sacabank.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.sacabank.ProductDocument.ProductDocumentRepository;
import com.project.sacabank.banner.BannerRepository;
import com.project.sacabank.blog.BlogRepository;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.productCategory.ProductCategoryRepository;
import com.project.sacabank.productComment.ProductCommentRepository;
import com.project.sacabank.vendorDocument.VendorDocumentRepository;

import jakarta.persistence.EntityManager;

@Component
public class FullRepo {
  @Autowired
  public ProductRepository productRepository;

  @Autowired
  public ProductCategoryRepository productCategoryRepository;

  @Autowired
  public BlogRepository blogRepository;

  @Autowired
  public BannerRepository bannerRepository;

  @Autowired
  public EntityManager entityManager;

  @Autowired
  public ProductCommentRepository productCommentRepository;

  @Autowired
  public VendorDocumentRepository vendorDocumentRepository;

  @Autowired
  public ProductDocumentRepository productDocumentRepository;
}
