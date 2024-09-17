package com.project.sacabank.cart;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class CartSpecifications {
    public static Specification<CartModel> equalUserId(UUID userId) {
        return (root, query, builder) -> builder.equal(root.get("userId"), userId);
    }

    public static Specification<CartModel> equalProductId(UUID productId) {
        return (root, query, builder) -> builder.equal(root.get("productId"), productId);
    }

    public static Specification<CartModel> searchByProduct(String search) {
        return (root, query, builder) -> builder.like(root.get("product").get("name"), "%" + search + "%");
    }
}
