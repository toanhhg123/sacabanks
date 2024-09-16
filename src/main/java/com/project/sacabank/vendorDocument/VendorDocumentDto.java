package com.project.sacabank.vendorDocument;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

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
public class VendorDocumentDto extends BaseDto {
    private String title;
    private UUID userId;
    private String file;
    private MultipartFile fileData;

}
