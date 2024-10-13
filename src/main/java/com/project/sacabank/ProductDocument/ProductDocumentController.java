package com.project.sacabank.ProductDocument;

import static com.project.sacabank.utils.Constants.PRODUCT_DOCUMENT_API;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.vendorDocument.VendorDocumentDto;

@RestController
@RequestMapping(path = PRODUCT_DOCUMENT_API)
public class ProductDocumentController extends BaseController {
    @Autowired
    ProductDocumentService service;

    @GetMapping("")
    public ResponseEntity<?> gets(
            @RequestParam Optional<UUID> productId,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {

        return this.onSuccess(service.gets(productId, page, limit));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @ModelAttribute VendorDocumentDto vendorDocumentDto) {
        vendorDocumentDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(service.create(vendorDocumentDto));
    }

    @PatchMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable("id") UUID id,
            @ModelAttribute VendorDocumentDto vendorDocumentDto) {

        vendorDocumentDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(service.update(id, vendorDocumentDto));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID id) {

        return this.onSuccess(service.removeById(id));
    }

}
