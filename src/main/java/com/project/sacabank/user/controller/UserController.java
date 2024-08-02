package com.project.sacabank.user.controller;

import static com.project.sacabank.utils.Constants.API_USER_PATH;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.enums.EnumNameRole;
import com.project.sacabank.user.dto.UserDto;
import com.project.sacabank.user.dto.UserUpdateRole;
import com.project.sacabank.user.model.User;
import com.project.sacabank.user.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = API_USER_PATH)
public class UserController extends BaseController {

  @Autowired
  UserService service;

  @GetMapping("")
  public ResponseEntity<?> gets(
      @RequestParam(defaultValue = "") String search,
      @RequestParam Optional<EnumNameRole> role,
      @RequestParam Optional<Integer> page,
      @RequestParam Optional<Integer> limit) {
    var data = this.service.getAllUser(search, role.isEmpty() ? null : role.get(), page, limit);
    return this.onSuccess(data);
  }

  @GetMapping("/vendor")
  public ResponseEntity<?> getsVendor(@RequestParam(defaultValue = "") String search,
      @RequestParam Optional<Integer> page) {
    var data = this.service.getAllUserVendor(search, page);
    return this.onSuccess(data);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
    var data = this.service.findById(id);
    return this.onSuccess(data);
  }

  @GetMapping("me")
  public ResponseEntity<?> getMe() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User user = service.getUserByUsername(currentPrincipalName);
    return this.onSuccess(user);
  }

  @PostMapping("")
  public ResponseEntity<?> create(@Valid @RequestBody UserDto userCreate) throws MessagingException {
    User user = service.create(userCreate);
    return this.onSuccess(user);
  }

  @PatchMapping("{id}")
  public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserDto userDto) {
    User user = service.update(id, userDto);
    return this.onSuccess(user);
  }

  @PatchMapping("role/{id}")
  public ResponseEntity<?> updateRole(@PathVariable UUID id, @RequestBody UserUpdateRole role) {
    User user = service.updateRole(id, role);
    return this.onSuccess(user);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable UUID id) {
    User user = service.remove(id);
    return this.onSuccess(user);
  }
}
