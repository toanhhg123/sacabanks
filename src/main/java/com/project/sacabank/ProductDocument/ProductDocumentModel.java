package com.project.sacabank.ProductDocument;

import java.util.UUID;

import com.google.auto.value.AutoValue.Builder;
import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.vendorDocument.VendorDocumentDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "product_document")
public class ProductDocumentModel extends BaseModel {
    private String title;
    private UUID productId;
    private String file;

    @Override
    public BaseModel fromDto(BaseDto baseDto) {
        var dto = (ProductDocumentDto) baseDto;

        if (dto.getTitle() != null) {
            this.setTitle(dto.getTitle());
        }

        if (dto.getFile() != null) {
            this.setFile(dto.getFile());
        }

        if (dto.getProductId() != null) {
            this.setProductId(dto.getProductId());
        }

        return this;
    }
}
