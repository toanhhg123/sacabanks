package com.project.sacabank.banner;

import static com.project.sacabank.utils.Constants.BANNER_API;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.banner.dto.BannerDto;
import com.project.sacabank.base.BaseController;

@RestController
@RequestMapping(path = BANNER_API)
public class BannerController extends BaseController {
    @Autowired
    BannerService service;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam Optional<Boolean> isActive,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize) {
        return this.onSuccess(service.getAll(isActive, page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        return this.onSuccess(service.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody BannerDto body) {
        return this.onSuccess(service.create(body));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody BannerDto body) {
        return this.onSuccess(service.update(id, body));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> remove(@PathVariable UUID id) {
        return this.onSuccess(service.removeById(id));
    }

}
