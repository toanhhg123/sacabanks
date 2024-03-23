package com.project.sacabank.product;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.project.sacabank.product.model.Product;
import com.project.sacabank.user.model.User;

public class ProductSpecifications {
  public static Specification<Product> titleIsLike(String title) {
    return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
  }

  public static Specification<Product> itemNumberIsLike(String itemNumber) {
    return (root, query, builder) -> builder.like(root.get("itemNumber"), "%" + itemNumber + "%");
  }

  public static Specification<Product> isEqualUser(User user) {
    return (root, query, builder) -> builder.equal(root.get("user"), user);
  }

  public static Specification<Product> isEqualCategoryId(UUID category_id) {
    return (root, query, builder) -> builder.equal(root.get("category").get("id"), category_id);
  }

  public static Specification<Product> isEqualUserId(UUID id) {
    return (root, query, builder) -> builder.equal(root.get("user").get("id"), id);
  }

  public static Specification<Product> isNullQuantity() {
    return (root, query, builder) -> builder.isNull(root.get("quantity"));
  }

}
