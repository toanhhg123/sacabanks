package com.project.sacabank.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        var products = homeService.getProductHome();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }
}
