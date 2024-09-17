package com.project.sacabank.wishlist;

import java.util.Optional;
import java.util.UUID;

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

import com.project.sacabank.base.BaseController;
import com.project.sacabank.utils.Constants;
import com.project.sacabank.wishlist.dto.WishlistDto;

@RestController
@RequestMapping(Constants.WISHLIST_API)
public class WishlistController extends BaseController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<?> getWishlistPagination(
            @RequestParam(required = false) Optional<String> search,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> limit) {
        return this.onSuccess(wishlistService.getWishlistPagination(getUserServiceInfo().getId(), search, page, limit));
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistDto wishlistDto) {
        wishlistDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(wishlistService.create(wishlistDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable UUID id) {
        return this.onSuccess(wishlistService.removeById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateWishlist(@PathVariable UUID id, @RequestBody WishlistDto wishlistDto) {
        wishlistDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(wishlistService.update(id, wishlistDto));
    }
}
