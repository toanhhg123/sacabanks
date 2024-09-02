package com.project.sacabank.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.category.dto.CategoryWithCountProduct;
import com.project.sacabank.category.model.Category;

@Repository
public interface CategoryRepository extends BaseRepository<Category, UUID> {
  java.util.List<Category> findAll(Specification<Category> spec);

  @Query("SELECT c FROM Category c Where c.rank > 0")
  java.util.List<Category> getWhiteList();

  @Query(value = """
      WITH RECURSIVE category_hierarchy AS (
          SELECT id, name, category_id,
                 CONCAT(id) AS path
          FROM category
          WHERE id = :uuid

          UNION ALL

          SELECT c.id, c.name, c.category_id, c.id
          FROM category c
          JOIN category_hierarchy ch ON c.id = ch.category_id
      )
      SELECT BIN_TO_UUID(id)
      FROM category_hierarchy
      ORDER BY LENGTH(path) DESC, path;
      """, nativeQuery = true)
  List<UUID> findCategoryHierarchy(@Param("uuid") UUID uuid);

  @Query(value = "SELECT c.*, COUNT(cp.id) as product_quantity " +
      "FROM category c " +
      "LEFT JOIN category_product cp ON c.id = cp.category_id " +
      "GROUP BY c.id", nativeQuery = true)
  List<CategoryWithCountProduct> findAllCategoriesWithProductCount();

}

// WITH RECURSIVE

// category_hierarchy AS (
// SELECT id, name, category_id,
// CONCAT(BIN_TO_UUID(id)) AS path
// FROM category

// WHERE BIN_TO_UUID(id) = BIN_TO_UUID(:uuid)

// UNION ALL

// SELECT c.id, c.name, c.category_id, BIN_TO_UUID(c.id)
// FROM category c
// JOIN category_hierarchy ch ON BIN_TO_UUID(c.id) = BIN_TO_UUID(ch.category_id)
// )

// SELECT BIN_TO_UUID(id)
// FROM category_hierarchy
// ORDER BY LENGTH(path) DESC, path;