package com.project.sacabank.blog;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.project.sacabank.base.BaseRepository;

@Repository
public interface BlogRepository extends BaseRepository<BlogModel, UUID> {
  Optional<BlogModel> findBySlug(String slug);
}
