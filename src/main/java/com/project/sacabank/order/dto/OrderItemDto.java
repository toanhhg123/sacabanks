package com.project.sacabank.order.dto;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class OrderItemDto extends BaseDto {
  @NotNull
  @NotBlank
  private UUID productId;
  private UUID orderId;
  private Integer quantity;

}
