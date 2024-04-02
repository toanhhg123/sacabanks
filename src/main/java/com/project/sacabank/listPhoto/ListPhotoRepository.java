package com.project.sacabank.listPhoto;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.sacabank.base.BaseRepository;

public interface ListPhotoRepository extends BaseRepository<ListPhoto, UUID> {
  @Modifying
  @Query("DELETE FROM ListPhoto lp WHERE lp.productId = :productId")
  void deleteByProductId(@Param("productId") UUID productId);

}
