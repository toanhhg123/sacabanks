package com.project.sacabank.web.home.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthPageController {

    @GetMapping("/dang-nhap")
    public String login() {

        return "login";
    }

}
