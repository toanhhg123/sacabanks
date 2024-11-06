package com.project.sacabank.auth.controller;

import static com.project.sacabank.utils.Constants.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.auth.dto.UserLoginDto;
import com.project.sacabank.auth.dto.UserRegisterDto;
import com.project.sacabank.auth.service.AuthService;
import com.project.sacabank.base.ResponseObject;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = API_AUTH_PATH)
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("register")
  public ResponseEntity<ResponseObject> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
    var data = authService.registerUser(userRegisterDto);
    return this.onSuccess(data);
  }

  @PostMapping("login")
  public ResponseEntity<ResponseObject> login(@Valid @RequestBody UserLoginDto userLoginDto) {
    var data = authService.loginToken(userLoginDto);
    return this.onSuccess(data);
  }

  public ResponseEntity<ResponseObject> onSuccess(Object data) {
    return new ResponseEntity<>(ResponseObject.builder().status(true).message("success").data(data).build(),
        HttpStatus.OK);
  }

}
