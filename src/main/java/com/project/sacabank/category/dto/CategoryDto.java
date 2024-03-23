package com.project.sacabank.category.dto;

import com.project.sacabank.base.BaseDto;

import jakarta.validation.constraints.NotBlank;
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
public class CategoryDto extends BaseDto {

  @NotBlank(message = "name can not blank")
  @NotNull(message = "name is not null")
  private String name;

  private String image;
  private Integer rank;

}
