package com.project.sacabank.web.home.dto;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Data
@NoArgsConstructor
@Entity
public class ProductDtoHomeQuery {
    @Id
    private UUID id;
    private String title;
    private String slug;
    private String userProviderName;
    private String mainPhoto;
    private Double price;
    private String tags;
}
