package com.project.sacabank.productComment.dto;

import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.auto.value.AutoValue.Builder;
import com.project.sacabank.base.BaseModel;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class ProductCommentQueryDto extends BaseModel {

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String title;
    private UUID userId;
    private UUID productId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private UserInfo user;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private ProductInfo product;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfo {
        private UUID id;
        private String username;
        private String email;
        private String avatar;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductInfo {
        private UUID id;
        private String title;
        private String slug;
        private String mainPhoto;
    }
}
