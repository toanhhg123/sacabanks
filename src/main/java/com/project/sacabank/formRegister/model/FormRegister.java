package com.project.sacabank.formRegister.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.enums.ECollab;
import com.project.sacabank.enums.EProfits;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class FormRegister extends BaseModel {
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

  @Column(columnDefinition = "boolean default false")
  @JsonProperty("isActive") // Specify the name for the JSON response
  private boolean isActive;

  @Enumerated(EnumType.STRING)
  ECollab collab;

  @Enumerated(EnumType.STRING)
  EProfits profits;

}