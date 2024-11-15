package com.project.sacabank.wishlist;

import java.util.Optional;
import java.util.UUID;

import com.project.sacabank.base.BaseRepository;

import jakarta.transaction.Transactional;

public interface WishlistRepository extends BaseRepository<WishlistModel, UUID> {

    Optional<WishlistModel> findByProductIdAndUserId(UUID productId, UUID userId);

    @Transactional
    void removeByProductIdAndUserId(UUID productId, UUID userId);

}
