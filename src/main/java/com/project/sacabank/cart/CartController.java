package com.project.sacabank.cart;

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
import com.project.sacabank.base.ResponseObject;
import com.project.sacabank.cart.dto.CartDto;
import com.project.sacabank.utils.Constants;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(Constants.CART_API)
@AllArgsConstructor
public class CartController extends BaseController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ResponseObject> getCart(
            @RequestParam Optional<String> search,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {
        return this.onSuccess(cartService.getCartPagination(getUserServiceInfo().getId(), search, page, limit));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addToCart(@RequestBody CartDto cartDto) {
        cartDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(cartService.create(cartDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseObject> removeFromCart(@PathVariable UUID id) {
        return this.onSuccess(cartService.removeById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ResponseObject> updateCart(@PathVariable UUID id, @RequestBody CartDto cartDto) {
        cartDto.setUserId(getUserServiceInfo().getId());
        return this.onSuccess(cartService.update(id, cartDto));
    }

}
