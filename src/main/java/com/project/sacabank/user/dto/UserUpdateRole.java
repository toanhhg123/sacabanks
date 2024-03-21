package com.project.sacabank.user.dto;

import com.project.sacabank.enums.EnumNameRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRole {

  @NotBlank(message = "role not blank")
  @NotNull(message = "role not null")
  private EnumNameRole role;
}
