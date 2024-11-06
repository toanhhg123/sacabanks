package com.project.sacabank.global;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.sacabank.auth.UserDetailsImpl;

@ControllerAdvice
public class GlobalCartControllerAdvice {

    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {

            return 20;
        }

        return 0;
    }

}
