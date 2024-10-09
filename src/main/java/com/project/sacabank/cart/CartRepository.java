package com.project.sacabank.cart;

import java.util.List;
import java.util.UUID;

import com.project.sacabank.base.BaseRepository;

public interface CartRepository extends BaseRepository<CartModel, UUID> {
    List<CartModel> findByUserId(UUID userId);
}
