package com.project.sacabank.product.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.google.common.base.Optional;
import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.product.model.Product;

import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends BaseRepository<Product, UUID> {

  Optional<Product> findBySlug(String slug);

  @Transactional
  @Modifying
  @Query("UPDATE Product p SET p.user = null WHERE p.user.id = :userId")
  void updateUserAssociationToNull(UUID userId);

  @Transactional
  @Modifying
  @Query("UPDATE Product p SET p.category = null WHERE p.category.id = :categoryId")
  void updateCategoryAssociationToNull(UUID categoryId);

  @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.slug = :slug AND p.id != :id")
  boolean existsBySlug(@Param("slug") String slug, @Param("id") UUID id);
}
