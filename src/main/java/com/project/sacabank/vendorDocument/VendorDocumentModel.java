package com.project.sacabank.vendorDocument;

import java.util.UUID;

import com.google.auto.value.AutoValue.Builder;
import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;

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
@Table(name = "vendor_document")
public class VendorDocumentModel extends BaseModel {
    private String title;
    private UUID userId;
    private String file;

    @Override
    public BaseModel fromDto(BaseDto baseDto) {
        var dto = (VendorDocumentDto) baseDto;

        if (dto.getTitle() != null) {
            this.setTitle(dto.getTitle());
        }

        if (dto.getFile() != null) {
            this.setFile(dto.getFile());
        }

        if (dto.getUserId() != null) {
            this.setUserId(dto.getUserId());
        }

        return this;
    }
}
