package com.project.sacabank.global;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.sacabank.auth.UserDetailsImpl;
import com.project.sacabank.cart.CartRepository;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalCartControllerAdvice {

    private final CartRepository cartRepository;

    @ModelAttribute("cartItemCount")
    public long cartItemCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {

            return cartRepository.countByUserId(userDetails.getId());
        }

        return 0;
    }

}
