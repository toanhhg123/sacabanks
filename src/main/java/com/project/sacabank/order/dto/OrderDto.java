package com.project.sacabank.order.dto;

import java.util.List;
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
public class OrderDto extends BaseDto {

  private UUID userId;
  private List<OrderItemDto> orderItemDtos;
}
