package com.project.sacabank.cart.dto;

import java.util.UUID;

import com.project.sacabank.base.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CartDto extends BaseDto {
    private UUID productId;
    private UUID userId;
    private Integer quantity;
}
