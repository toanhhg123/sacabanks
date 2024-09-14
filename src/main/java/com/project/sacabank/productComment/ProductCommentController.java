package com.project.sacabank.productComment;

import static com.project.sacabank.utils.Constants.PRODUCT_COMMENT_API;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.productComment.dto.ProductCommentDto;

@RestController
@RequestMapping(path = PRODUCT_COMMENT_API)
public class ProductCommentController extends BaseController {
    @Autowired
    ProductCommentService service;

    @GetMapping("{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        return this.onSuccess(service.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProductCommentDto body) {
        body.setUserId(getUserInfo().getId());
        return this.onSuccess(service.create(body));
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> productComment(
            @PathVariable UUID id,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {
        return this.onSuccess(service.getByProductId(id, page, limit));

    }

    @GetMapping("product/preview/{id}")
    public ResponseEntity<?> productCommentPreview(
            @PathVariable UUID id) {
        return this.onSuccess(service.getByProductId(id, Optional.of(0), Optional.of(2)));

    }
}
