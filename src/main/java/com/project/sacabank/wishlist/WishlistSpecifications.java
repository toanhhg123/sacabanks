package com.project.sacabank.wishlist;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class WishlistSpecifications {

    public static Specification<WishlistModel> equalUserId(UUID userId) {
        return (root, query, builder) -> builder.equal(root.get("userId"), userId);
    }

    public static Specification<WishlistModel> searchByProduct(String search) {
        return (root, query, builder) -> builder.like(root.get("product").get("name"), "%" + search + "%");
    }
}
