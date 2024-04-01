package com.project.sacabank.listPhoto;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.project.sacabank.base.BaseRepository;

public interface ListPhotoRepository extends BaseRepository<ListPhoto, UUID> {
  @Query("DELETE FROM ListPhoto p WHERE p.productId = :productId")
  void deleteByProductId(UUID productId);
}
