package com.project.sacabank.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.product.dto.ProductDto;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "order_item")
public class OrderItem extends BaseModel {
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne()
  @JsonIgnore
  private Order order;

  private Integer quantity;
}
