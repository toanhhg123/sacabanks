package com.project.sacabank.productComment.dto;

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
public class ProductCommentDto extends BaseDto {
    private String content;
    private String title;
    private UUID userId;
    private UUID productId;
}
