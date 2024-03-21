package com.project.sacabank.Role.controller;

import static com.project.sacabank.utils.Constants.API_ROLE_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.Role.model.Role;
import com.project.sacabank.Role.repository.RoleRepository;
import com.project.sacabank.base.ResponseObject;
import com.project.sacabank.enums.EnumNameRole;

@RestController
@RequestMapping(path = API_ROLE_PATH)
public class RoleController {

  @Autowired
  RoleRepository roleRepository;

  @GetMapping("")
  public ResponseEntity<ResponseObject> gets() {
    var data = roleRepository.findAll();
    return this.onSuccess(data);
  }

  @GetMapping("/seed")
  public ResponseEntity<ResponseObject> seed() {
    List<Role> listRole = List.of(
        Role.builder().name(EnumNameRole.SUPPER_ADMIN).build(),
        Role.builder().name(EnumNameRole.ADMIN).build(),
        Role.builder().name(EnumNameRole.CLIENT).build(),
        Role.builder().name(EnumNameRole.VENDOR).build());

    var data = roleRepository.saveAll(listRole);
    return this.onSuccess(data);
  }

  public ResponseEntity<ResponseObject> onSuccess(Object data) {
    return new ResponseEntity<>(ResponseObject.builder().status(true).message("success").data(data).build(),
        HttpStatus.OK);
  }

}
