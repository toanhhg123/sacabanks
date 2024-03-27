package com.project.sacabank.category;

import org.springframework.data.jpa.domain.Specification;

import com.project.sacabank.category.model.Category;

public class CategorySpecifications {
  public static Specification<Category> nameIsLike(String name) {
    return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
  }

}
