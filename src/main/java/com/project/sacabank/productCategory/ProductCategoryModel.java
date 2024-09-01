package com.project.sacabank.productCategory;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.productCategory.dto.ProductCategoryCreate;

import jakarta.persistence.Entity;
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
@Table(name = "category_product")
public class ProductCategoryModel extends BaseModel {
  private UUID categoryId;

  private UUID productId;

  @Override
  public BaseModel fromDto(BaseDto baseDto) {
    var dto = (ProductCategoryCreate) baseDto;

    if (dto.getCategoryId() != null) {
      this.setCategoryId(dto.getCategoryId());
    }

    if (dto.getProductId() != null) {
      this.setProductId(dto.getProductId());
    }

    return this;
  }
}
