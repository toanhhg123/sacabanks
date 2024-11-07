package com.project.sacabank.cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.sacabank.base.BaseRepository;

public interface CartRepository extends BaseRepository<CartModel, UUID> {
    List<CartModel> findByUserId(UUID userId);

    Optional<CartModel> findByProductIdAndUserId(UUID productId, UUID userId);

    long countByUserId(UUID userId);
}
