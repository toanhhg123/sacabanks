package com.project.sacabank.productCompare;

import java.util.List;
import java.util.UUID;

import com.project.sacabank.base.BaseRepository;

public interface ProductCompareRepository extends BaseRepository<ProductCompare, UUID> {

    List<ProductCompare> findByUserId(UUID userId);

    boolean existsByUserIdAndProductId(UUID userId, UUID productId);

}
