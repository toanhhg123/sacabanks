package com.project.sacabank.ProductDocument;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class ProductDocumentSpecifications {
    public static Specification<ProductDocumentModel> equalByProductId(UUID productId) {
        return (root, query, builder) -> builder.equal(root.get("productId"), productId);
    }
}
