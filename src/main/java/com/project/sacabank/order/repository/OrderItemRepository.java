package com.project.sacabank.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.order.model.OrderItem;

public interface OrderItemRepository extends BaseRepository<OrderItem, UUID> {
  @Transactional
  @Modifying
  @Query("DELETE FROM OrderItem oi WHERE oi.order.id = :orderId")
  void deleteByOrder_id(UUID orderId);
}