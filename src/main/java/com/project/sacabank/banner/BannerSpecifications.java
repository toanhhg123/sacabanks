package com.project.sacabank.banner;

import org.springframework.data.jpa.domain.Specification;

public class BannerSpecifications {
    public static Specification<BannerModel> isActive(boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            return isActive ? criteriaBuilder.isTrue(root.get("isActive"))
                    : criteriaBuilder.isFalse(root.get("isActive"));
        };
    }

}
