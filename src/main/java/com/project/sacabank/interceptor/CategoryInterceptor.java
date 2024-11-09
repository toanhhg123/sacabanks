package com.project.sacabank.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.sacabank.repositories.CategoryRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CategoryInterceptor implements HandlerInterceptor {

    private final CategoryRepository categoryRepository;

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {

        if (request.getRequestURI().startsWith("/api"))
            return;

        var list = categoryRepository.findCategoriesWithNullCategoryId();
        request.setAttribute("categoriesHeader", list);

    }
}
