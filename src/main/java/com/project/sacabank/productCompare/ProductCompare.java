package com.project.sacabank.productCompare;

import java.util.UUID;

import com.project.sacabank.base.BaseModel;
import com.project.sacabank.product.model.Product;

import jakarta.persistence.Column;
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
@Table(name = "product_compare")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ProductCompare extends BaseModel {
    private UUID userId;

    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;
}
