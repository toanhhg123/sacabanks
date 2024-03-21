package com.project.sacabank.auth.dto;

import com.project.sacabank.base.BaseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserLoginDto extends BaseDto {

  @NotBlank(message = "username is mandatory")
  String username;

  @NotBlank(message = "password is mandatory")
  @Size(min = 6, max = 16, message = "password range from 6 to 16 character")
  String password;
}
