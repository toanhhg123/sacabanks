package com.project.sacabank.user.service;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.Role.repository.RoleRepository;
import com.project.sacabank.enums.EnumNameRole;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.order.repository.OrderRepository;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.user.UserSpecifications;
import com.project.sacabank.user.dto.UserDto;
import com.project.sacabank.user.dto.UserUpdateRole;
import com.project.sacabank.user.model.User;
import com.project.sacabank.user.repository.UserRepository;;

@Service
public class UserService {

  @Autowired
  @Qualifier("userRepository")
  UserRepository userRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  ModelMapper mapper;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  OrderRepository orderRepository;

  public User getUserByUsername(String username) {
    var user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      CustomException.throwError("not found user");
    }

    return user.get();
  }

  public List<User> getAllUser(String keyword, EnumNameRole role, Optional<Integer> page) {
    Specification<User> spec = Specification.where(null);
    var pageNumber = page.isPresent() ? page.get() : 0;

    if (keyword != null && !keyword.isEmpty()) {
      spec = spec.or(UserSpecifications.usernameIsLike(keyword));
      spec = spec.or(UserSpecifications.phoneNumberIsLike(keyword));
      spec = spec.or(UserSpecifications.emailIsLike(keyword));
    }

    if (role != null) {
      var role_db = roleRepository.findByName(role);
      spec = spec.and(UserSpecifications.roleIsEqual(role_db.get()));
    }

    return userRepository.findAll(spec, PageRequest.of(pageNumber, PAGE_SIZE));

  }

  public List<User> getAllUserVendor(String keyword, Optional<Integer> page) {
    Specification<User> spec = Specification.where(null);
    var pageNumber = page.isPresent() ? page.get() : 0;

    if (keyword != null && !keyword.isEmpty()) {
      spec = spec.and(UserSpecifications.usernameIsLike(keyword));
      spec = spec.or(UserSpecifications.phoneNumberIsLike(keyword));
      spec = spec.or(UserSpecifications.emailIsLike(keyword));
    }

    var role_db = roleRepository.findByName(EnumNameRole.VENDOR);
    spec = spec.and(UserSpecifications.roleIsEqual(role_db.get()));

    return userRepository.findAll(spec, PageRequest.of(pageNumber, PAGE_SIZE));

  }

  public User create(UserDto userCreate) {
    boolean existsByEmail = userRepository.existsByEmail(userCreate.getEmail());
    boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userCreate.getPhoneNumber());
    boolean existsByUsername = userRepository.existsByEmail(userCreate.getUsername());

    if (existsByEmail) {
      CustomException.throwError("email have exist");
    }

    if (existsByPhoneNumber) {
      CustomException.throwError("phone number have exist");
    }

    if (existsByUsername) {
      CustomException.throwError("username have exist");
    }

    userCreate.setPassword(encoder.encode(userCreate.getPassword()));
    var user = mapper.map(userCreate, User.class);
    var role = roleRepository.findByName(userCreate.getRole());

    if (user == null || role == null) {
      throw new CustomException("user or role is null");
    }

    user.setRole(role.get());

    return userRepository.save(user);
  }

  public User update(UUID id, UserDto userDto) {
    boolean existsByEmail = userRepository.existsByEmailAndNotUserId(userDto.getEmail(), id);

    if (existsByEmail) {
      CustomException.throwError("email have exist");
    }

    boolean existsByPhoneNumber = userRepository.existsByPhoneNumberAndNotUserId(userDto.getEmail(), id);

    if (existsByPhoneNumber) {
      CustomException.throwError("phone number have exist");
    }

    var user = userRepository.findById(id);
    user.get().updateUserDto(userDto);
    return userRepository.save(user.get());

  }

  public User updateRole(UUID id, UserUpdateRole role) {
    var roleDb = roleRepository.findByName(role.getRole());
    var user = userRepository.findById(id);

    if (roleDb.isEmpty())
      throw new CustomException("not found role");
    if (user.isEmpty())
      throw new CustomException("not found user");

    user.get().setRole(roleDb.get());
    return userRepository.save(user.get());

  }

  public User findById(UUID user_id) {
    var user = userRepository.findById(user_id);

    if (user.isEmpty()) {
      throw new CustomException("not found user");
    }

    return user.get();
  }

  @Transactional
  public User remove(UUID id) {
    var user = userRepository.findById(id);

    if (!user.isPresent()) {
      throw new CustomException("not found user");
    }
    productRepository.updateUserAssociationToNull(id);
    orderRepository.deleteByUserId(user.get().getId());
    userRepository.deleteById(id);
    return user.get();

  }
}
