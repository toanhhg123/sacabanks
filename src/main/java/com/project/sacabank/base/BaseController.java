package com.project.sacabank.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.sacabank.auth.UserDetailsImpl;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.user.model.User;
import com.project.sacabank.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
public class BaseController {

  @Autowired
  UserRepository userRepository;

  // @Autowired
  // protected TService service;

  // public BaseController(TService service) {
  // this.service = service;
  // }

  // @GetMapping("")
  // public ResponseEntity<ResponseObject> gets() {
  // var data = service.getAll();
  // return this.onSuccess(data);
  // }

  // public <TYPE_DTO extends BaseDto> ResponseEntity<ResponseObject>
  // create(@Valid TYPE_DTO dto,
  // Class<TModel> returnType) {
  // var data = this.service.create(dto, returnType);
  // return this.onSuccess(data);
  // }

  public ResponseEntity<ResponseObject> onSuccess(Object data) {
    return new ResponseEntity<>(ResponseObject.builder().status(true).message("success").data(data).build(),
        HttpStatus.OK);
  }

  public UserDetailsImpl getUserServiceInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
      return (UserDetailsImpl) authentication.getPrincipal();
    }

    throw new CustomException("Get user fail");

  }

  public User getUserInfo() {
    var useService = this.getUserServiceInfo();
    var user = userRepository.findById(useService.getId());

    if (user.isEmpty()) {
      throw new CustomException("Get user fail");
    }

    return user.get();

  }

}
