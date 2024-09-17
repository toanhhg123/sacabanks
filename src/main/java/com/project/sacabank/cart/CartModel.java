package com.project.sacabank.cart;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.cart.dto.CartDto;

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
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "cart")
public class CartModel extends BaseModel {
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "user_id")
    private UUID userId;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductInfo product;

    @Override
    public BaseModel fromDto(BaseDto baseDto) {
        var dto = (CartDto) baseDto;

        if (dto.getProductId() != null) {
            this.setProductId(dto.getProductId());
        }

        if (dto.getUserId() != null) {
            this.setUserId(dto.getUserId());
        }

        if (dto.getQuantity() != null) {
            this.setQuantity(dto.getQuantity() > 0 ? dto.getQuantity() : 1);
        }

        return this;
    }

    @Entity
    @Table(name = "product")
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class ProductInfo extends BaseModel {
        private String title;
        private String slug;
        private String itemNumber;
        private String material;
        private String finishing;
        private Float dimensionL;
        private Float dimensionW;
        private Float dimensionH;
        private Float netWeight;
        private Double price;
        private Integer quantity;
        private String mainPhoto;
        private String tags;
    }

}
