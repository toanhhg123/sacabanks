package com.project.sacabank.productComment;

import static com.project.sacabank.utils.Constants.PRODUCT_COMMENT_API;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.base.ResponseObject;
import com.project.sacabank.productComment.dto.ProductCommentDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = PRODUCT_COMMENT_API)
@RequiredArgsConstructor
public class ProductCommentController extends BaseController {

    private final ProductCommentService service;

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> findOne(@PathVariable UUID id) {
        return this.onSuccess(service.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> create(@RequestBody ProductCommentDto body) {
        body.setUserId(getUserInfo().getId());
        return this.onSuccess(service.create(body));
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ResponseObject> productComment(
            @PathVariable UUID id,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {
        return this.onSuccess(service.getByProductId(id, page, limit));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ResponseObject> updateProductComment(
            @PathVariable UUID id,
            @RequestBody ProductCommentDto body) {
        return this.onSuccess(service.update(id, body));
    }

    @GetMapping("manager")
    public ResponseEntity<ResponseObject> getProductCommentManager(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {
        return this.onSuccess(service.getProductCommentManager(page, limit));

    }

    @GetMapping("product/preview/{id}")
    public ResponseEntity<ResponseObject> productCommentPreview(
            @PathVariable UUID id) {
        return this.onSuccess(service.getByProductId(id, Optional.of(0), Optional.of(2)));

    }

}
