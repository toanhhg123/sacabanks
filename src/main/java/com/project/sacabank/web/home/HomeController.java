package com.project.sacabank.web.home;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.sacabank.blog.BlogRepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    private final BlogRepository blogRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        var products = homeService.getProductHome();
        var blogs = blogRepository.findAll(Specification.where(null), PageRequest.of(0, 8));
        model.addAttribute("products", products);
        model.addAttribute("blogs", blogs);

        return "home";
    }

    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }

    @GetMapping("/san-pham")
    public String viewProduct() {
        return "product";
    }

    @GetMapping("/gio-hang")
    public String viewCart() {
        return "cart";
    }

}
