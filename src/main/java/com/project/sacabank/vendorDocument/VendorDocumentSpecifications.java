package com.project.sacabank.vendorDocument;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class VendorDocumentSpecifications {
    public static Specification<VendorDocumentModel> equalUserId(UUID userId) {
        return (root, query, builder) -> builder.equal(root.get("userId"), userId);
    }
}
