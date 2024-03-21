package com.project.sacabank.order.model;

import java.util.List;

import com.project.sacabank.base.BaseModel;
import com.project.sacabank.product.dto.ProductDto;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "orders")
public class Order extends BaseModel {
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "id")
  private User user;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "order_item", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "id"))
  private List<OrderItem> orderItems;

}
