package com.project.sacabank.category.dto;

import java.util.UUID;

import com.project.sacabank.base.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CategoryWithCountProduct extends BaseModel {
    private String name;
    private String image;
    @Column(name = "`rank`")
    private Integer rank;
    private UUID categoryId;
    private Long productQuantity;

}
