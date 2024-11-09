package com.project.sacabank.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.sacabank.auth.UserDetailsImpl;
import com.project.sacabank.cart.CartRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CartInterceptor implements HandlerInterceptor {

    private final CartRepository cartRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView)
            throws Exception {

        if (request.getRequestURI().startsWith("/api"))
            return;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            var numberCart = cartRepository.countByUserId(userDetails.getId());
            request.setAttribute("cartItemCount", numberCart);

        }

    }

}
