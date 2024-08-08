package com.project.sacabank.productCategory.dto;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;

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
public class ProductCategoryCreate extends BaseDto {
  private UUID categoryId;
  private UUID productId;
}
