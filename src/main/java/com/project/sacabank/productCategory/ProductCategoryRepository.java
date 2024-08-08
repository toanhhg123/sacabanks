package com.project.sacabank.productCategory;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.project.sacabank.base.BaseRepository;

@Repository
public interface ProductCategoryRepository extends BaseRepository<ProductCategoryModel, UUID> {

}
