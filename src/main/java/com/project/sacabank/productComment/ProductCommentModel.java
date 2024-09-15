package com.project.sacabank.productComment;

import java.util.UUID;

import com.google.auto.value.AutoValue.Builder;
import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.productComment.dto.ProductCommentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// CREATE TABLE product_comment (
//     id BINARY(16) PRIMARY KEY, 
//     content TEXT,
//     title VARCHAR(250),
//     user_id BINARY(16),
//     product_id BINARY(16),
//     created_at DATETIME(6),
//     updated_at DATETIME(6),
//     FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
//     FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
// );

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "product_comment")
public class ProductCommentModel extends BaseModel {

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String title;
    private UUID userId;
    private UUID productId;
    private Boolean isActive;

    @Override
    public BaseModel fromDto(BaseDto baseDto) {
        var dto = (ProductCommentDto) baseDto;

        if (dto.getContent() != null) {
            this.setContent(dto.getContent());
        }

        if (dto.getTitle() != null) {
            this.setTitle(dto.getTitle());
        }

        if (dto.getIsActive() != null) {
            this.setIsActive(dto.getIsActive());
        }

        return this;
    }

}
