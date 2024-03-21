package com.project.sacabank.formRegister.dto;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.enums.ECollab;
import com.project.sacabank.enums.EProfits;

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
public class FormRegisterDto extends BaseDto {
  private String companyName;
  private String address;
  private String email;
  private String phone;
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
  private String shortNameCompany;
  private ECollab collab;
  private EProfits profits;
  private Boolean isActive;

}
