package com.project.sacabank.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;

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
  private Integer dimensionL;
  private Integer dimensionW;
  private Integer dimensionH;
  private Float netWeight;

  private Double price;
  private Integer quantity;
  private String mainPhoto;
  private String tags;

  private UUID categoryId;

}
