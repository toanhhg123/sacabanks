package com.project.sacabank.product.dto;

import java.util.List;
import java.util.UUID;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.product.model.DetailItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductDto extends BaseDto {
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
  private String desc;
  private UUID categoryId;
  private List<DetailItem> listDetails;
}
