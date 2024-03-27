package com.project.sacabank.repositories;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.category.model.Category;

@Repository
public interface CategoryRepository extends BaseRepository<Category, UUID> {
  java.util.List<Category> findAll(Specification<Category> spec);

}
