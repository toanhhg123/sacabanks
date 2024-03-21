package com.project.sacabank.user;

import org.springframework.data.jpa.domain.Specification;

import com.project.sacabank.Role.model.Role;
import com.project.sacabank.user.model.User;

public class UserSpecifications {
  public static Specification<User> usernameIsLike(String username) {
    return (root, query, builder) -> builder.like(root.get("username"), "%" + username + "%");
  }

  public static Specification<User> emailIsLike(String email) {
    return (root, query, builder) -> builder.like(root.get("email"), "%" + email + "%");
  }

  public static Specification<User> phoneNumberIsLike(String phoneNumber) {
    return (root, query, builder) -> builder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
  }

  public static Specification<User> roleIsEqual(Role role) {
    return (root, query, builder) -> builder.equal(root.get("role"), role);
  }
}
