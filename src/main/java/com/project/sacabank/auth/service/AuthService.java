package com.project.sacabank.auth.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.sacabank.Role.repository.RoleRepository;
import com.project.sacabank.auth.dto.UserLoginDto;
import com.project.sacabank.auth.dto.UserRegisterDto;
import com.project.sacabank.enums.EnumNameRole;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.user.model.User;
import com.project.sacabank.user.repository.UserRepository;
import com.project.sacabank.utils.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final PasswordEncoder encoder;

  private final RoleRepository roleRepository;

  private final ModelMapper mapper;

  private final AuthenticationManager authenticationManager;

  private final JwtUtils jwtUtils;

  public String loginToken(UserLoginDto userLoginDto) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userLoginDto.getUsername(), userLoginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return jwtUtils.generateJwtToken(authentication);
  }

  public User registerUser(UserRegisterDto userRegisterDto) {

    boolean existsByEmail = userRepository.existsByEmail(userRegisterDto.getEmail());
    boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userRegisterDto.getPhoneNumber());
    boolean existsByUsername = userRepository.existsByEmail(userRegisterDto.getUsername());
    var role = roleRepository.findByName(EnumNameRole.CLIENT);

    if (existsByEmail) {
      CustomException.throwError("email đã tồn tại");
    }

    if (existsByPhoneNumber) {
      CustomException.throwError("số điện thoại đã tồn tại");
    }

    if (existsByUsername) {
      CustomException.throwError("tên đăng nhập đã tồn tại");
    }

    User user = mapper.map(userRegisterDto, User.class);
    user.setRole(role.get());
    user.setPassword(encoder.encode(user.getPassword()));

    return userRepository.save(user);

  }

  public User getMe(String username) {
    var data = userRepository.findByUsername(username);

    if (data.isEmpty()) {
      CustomException.throwError("not found info user");
    }

    return data.get();
  }

}
