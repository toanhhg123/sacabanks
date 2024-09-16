package com.project.sacabank.ProductDocument;

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
public class ProductDocumentDto extends BaseDto {
    private MultipartFile fileData;
    private String title;
    private UUID productId;
    private String file;
}
