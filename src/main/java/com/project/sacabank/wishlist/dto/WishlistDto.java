package com.project.sacabank.wishlist.dto;

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
public class WishlistDto extends BaseDto {
    private UUID productId;
    private UUID userId;
}
