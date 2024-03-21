package com.project.sacabank.formRegister;

import org.springframework.data.jpa.domain.Specification;

import com.project.sacabank.formRegister.model.FormRegister;

public class FormRegisterSpecifications {
  public static Specification<FormRegister> companyNameIsLike(String companyName) {
    return (root, query, builder) -> builder.like(root.get("companyName"), "%" + companyName + "%");
  }

  public static Specification<FormRegister> companyGroupIsLike(String companyGroup) {
    return (root, query, builder) -> builder.like(root.get("companyGroup"), "%" + companyGroup + "%");
  }

  public static Specification<FormRegister> phoneIsLike(String phone) {
    return (root, query, builder) -> builder.like(root.get("phone"), "%" + phone + "%");
  }

  public static Specification<FormRegister> emailIsLike(String email) {
    return (root, query, builder) -> builder.like(root.get("email"), "%" + email + "%");
  }

}
