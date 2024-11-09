package com.project.sacabank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.sacabank.interceptor.CartInterceptor;
import com.project.sacabank.interceptor.CategoryInterceptor;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebMvc
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private final CartInterceptor cartInterceptor;
  private final CategoryInterceptor categoryInterceptor;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String projectRoot = System.getProperty("user.dir");
    String uploadPath = projectRoot + "/uploads/";

    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:" + uploadPath)
        .setCachePeriod(3600)
        .resourceChain(true);

    registry.addResourceHandler("/static/**")
        .addResourceLocations("classpath:/static/")
        .setCachePeriod(3600)
        .resourceChain(true);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(cartInterceptor)
        .addPathPatterns("/**");
    registry.addInterceptor(categoryInterceptor)
        .addPathPatterns("/**");
  }
}
