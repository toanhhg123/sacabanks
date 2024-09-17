package com.project.sacabank.wishlist;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.wishlist.dto.WishlistDto;

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
@Table(name = "wishlist")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class WishlistModel extends BaseModel {
    private UUID userId;

    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductInfo product;

    @Override
    public BaseModel fromDto(BaseDto baseDto) {
        WishlistDto dto = (WishlistDto) baseDto;
        if (dto.getUserId() != null) {
            this.setUserId(dto.getUserId());
        }
        if (dto.getProductId() != null) {
            this.setProductId(dto.getProductId());
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
