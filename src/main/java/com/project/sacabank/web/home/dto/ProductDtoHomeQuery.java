package com.project.sacabank.web.home.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDtoHomeQuery {
    private UUID id;
    private String title;
    private String slug;
    private String userProviderName;
    private String mainPhoto;
    private Double price;
    private String tags;
}
