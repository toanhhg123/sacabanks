package com.project.sacabank.user.dto;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.enums.ECollab;
import com.project.sacabank.enums.EProfits;
import com.project.sacabank.enums.EnumNameRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
public class UserDto extends BaseDto {

  @NotBlank(message = "username is mandatory")
  private String username;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "field must be email")
  private String email;

  @Size(min = 6, max = 16, message = "password range from 6 to 16 character")
  @NotBlank(message = "password is mandatory")
  private String password;

  @Min(message = "phone number is min 10 char", value = 10)
  @NotBlank(message = "phoneNumber is mandatory")
  private String phoneNumber;
  private EnumNameRole role;
  private String companyName;
  private String address;
  private String fullNameOwnerCompany;
  private String companyGroup;
  private String mainProductGroup;
  private String numberProductService;
  private String revenueEachYear;
  private String description;
  private String listCertificate;
  private String listLinkProduct;
  private String companyWishesCooperate;
  private String linkProfile;
  private String implementerName;
  private String implementerPhone;
  private String linkWebsite;
  private ECollab collab;
  private EProfits profits;
  private String shortNameCompany;
  private String avatar;
  private String banner;

}
