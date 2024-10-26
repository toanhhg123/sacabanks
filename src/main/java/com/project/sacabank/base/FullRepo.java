package com.project.sacabank.base;

import org.springframework.stereotype.Component;

import com.project.sacabank.ProductDocument.ProductDocumentRepository;
import com.project.sacabank.banner.BannerRepository;
import com.project.sacabank.blog.BlogRepository;
import com.project.sacabank.cart.CartRepository;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.productCategory.ProductCategoryRepository;
import com.project.sacabank.productComment.ProductCommentRepository;
import com.project.sacabank.vendorDocument.VendorDocumentRepository;
import com.project.sacabank.wishlist.WishlistRepository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FullRepo {

  public final ProductRepository productRepository;

  public final ProductCategoryRepository productCategoryRepository;

  public final BlogRepository blogRepository;

  public final BannerRepository bannerRepository;

  public final EntityManager entityManager;

  public final ProductCommentRepository productCommentRepository;

  public final VendorDocumentRepository vendorDocumentRepository;

  public final ProductDocumentRepository productDocumentRepository;

  public final CartRepository cartRepository;

  public final WishlistRepository wishlistRepository;
}
