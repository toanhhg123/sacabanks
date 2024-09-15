package com.project.sacabank.productComment.dto;

import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.auto.value.AutoValue.Builder;
import com.project.sacabank.base.BaseModel;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ProductCommentManagerDto extends BaseModel {
    private String title;
    private String slug;
    private String mainPhoto;
    private Double price;
    private Integer quantity;
    private Integer quantityCommentActive;
    private Integer quantityCommentNoActive;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private UserInfo user;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfo {
        private UUID id;
        private String username;
        private String email;
        private String avatar;
    }
}
