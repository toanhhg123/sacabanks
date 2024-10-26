package com.project.sacabank.vendorDocument;

import java.util.Optional;
import java.util.UUID;

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
import com.project.sacabank.base.ResponseObject;
import static com.project.sacabank.utils.Constants.VENDOR_DOCUMENT_API;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = VENDOR_DOCUMENT_API)
@AllArgsConstructor
public class VendorDocumentController extends BaseController {

    private final VendorDocumentService service;

    @GetMapping("")
    public ResponseEntity<ResponseObject> gets(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {

        Optional<UUID> userIdOptional = Boolean.TRUE.equals(isManager())
                ? Optional.empty()
                : Optional.of(getUserServiceInfo().getId());

        return this.onSuccess(service.gets(userIdOptional, page, limit));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> create(
            @ModelAttribute VendorDocumentDto vendorDocumentDto) {
        vendorDocumentDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(service.create(vendorDocumentDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> gets(@PathVariable UUID id) {
        return this.onSuccess(service.getById(id));
    }

    @PatchMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> update(
            @PathVariable("id") UUID id,
            @ModelAttribute VendorDocumentDto vendorDocumentDto) {

        vendorDocumentDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(service.update(id, vendorDocumentDto));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ResponseObject> delete(
            @PathVariable("id") UUID id) {

        return this.onSuccess(service.removeById(id));
    }

}
