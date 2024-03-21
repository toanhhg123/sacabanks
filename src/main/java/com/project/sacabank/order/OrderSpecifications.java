package com.project.sacabank.order;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.project.sacabank.order.model.Order;

public class OrderSpecifications {
  public static Specification<Order> userIdIsEqual(UUID userId) {
    return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
  }
}
