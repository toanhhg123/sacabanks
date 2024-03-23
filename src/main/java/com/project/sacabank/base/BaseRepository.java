package com.project.sacabank.base;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseModel, ID extends Serializable> extends JpaRepository<T, ID> {
  java.util.List<T> findAll(Specification<T> spec, Pageable pageable);

  Integer count(Specification<T> spec);

}
