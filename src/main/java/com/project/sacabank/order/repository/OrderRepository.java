package com.project.sacabank.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.order.model.Order;
import java.util.List;
import com.project.sacabank.user.model.User;

import jakarta.transaction.Transactional;

public interface OrderRepository extends BaseRepository<Order, UUID> {

  @Modifying
  @Query("DELETE FROM Order o WHERE o.user.id = :userId")
  void deleteByUserId(UUID userId);
}