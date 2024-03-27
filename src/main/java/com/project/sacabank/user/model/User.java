package com.project.sacabank.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sacabank.Role.model.Role;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.enums.ECollab;
import com.project.sacabank.enums.EProfits;
import com.project.sacabank.user.dto.UserDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "phoneNumber")
})
public class User extends BaseModel {

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  @JsonIgnore
  private String password;

  @NotBlank
  @Size(max = 120)
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role", nullable = false, referencedColumnName = "name")
  private Role role;

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
  private String shortNameCompany;
  private String avatar;
  private String banner;

  @Enumerated(EnumType.STRING)
  ECollab collab;

  @Enumerated(EnumType.STRING)
  EProfits profits;

  public void updateUserDto(UserDto userDto) {
    if (userDto.getUsername() != null && this.getUsername() != userDto.getUsername()) {
      this.setUsername(userDto.getUsername());
    }
    if (userDto.getEmail() != null && userDto.getEmail() != this.getEmail()) {
      this.setEmail(userDto.getEmail());
    }
    if (userDto.getPhoneNumber() != null && userDto.getPhoneNumber() != this.getPhoneNumber()) {
      this.setPhoneNumber(userDto.getPhoneNumber());
    }
    if (userDto.getCompanyName() != null) {
      this.setCompanyName(userDto.getCompanyName());
    }
    if (userDto.getAddress() != null) {
      this.setAddress(userDto.getAddress());
    }
    if (userDto.getFullNameOwnerCompany() != null) {
      this.setFullNameOwnerCompany(userDto.getFullNameOwnerCompany());
    }
    if (userDto.getCompanyGroup() != null) {
      this.setCompanyGroup(userDto.getCompanyGroup());
    }
    if (userDto.getMainProductGroup() != null) {
      this.setMainProductGroup(userDto.getMainProductGroup());
    }
    if (userDto.getNumberProductService() != null) {
      this.setNumberProductService(userDto.getNumberProductService());
    }
    if (userDto.getRevenueEachYear() != null) {
      this.setRevenueEachYear(userDto.getRevenueEachYear());
    }
    if (userDto.getDescription() != null) {
      this.setDescription(userDto.getDescription());
    }
    if (userDto.getListCertificate() != null) {
      this.setListCertificate(userDto.getListCertificate());
    }
    if (userDto.getListLinkProduct() != null) {
      this.setListLinkProduct(userDto.getListLinkProduct());
    }
    if (userDto.getCompanyWishesCooperate() != null) {
      this.setCompanyWishesCooperate(userDto.getCompanyWishesCooperate());
    }
    if (userDto.getLinkProfile() != null) {
      this.setLinkProfile(userDto.getLinkProfile());
    }
    if (userDto.getImplementerName() != null) {
      this.setImplementerName(userDto.getImplementerName());
    }
    if (userDto.getImplementerPhone() != null) {
      this.setImplementerPhone(userDto.getImplementerPhone());
    }
    if (userDto.getLinkWebsite() != null) {
      this.setLinkWebsite(userDto.getLinkWebsite());
    }
    if (userDto.getCollab() != null) {
      this.setCollab(userDto.getCollab());
    }
    if (userDto.getProfits() != null) {
      this.setProfits(userDto.getProfits());
    }
    if (userDto.getShortNameCompany() != null) {
      this.setShortNameCompany(userDto.getShortNameCompany());
    }

    if (userDto.getAvatar() != null) {
      this.setAvatar(userDto.getAvatar());
    }

    if (userDto.getBanner() != null) {
      this.setBanner(userDto.getBanner());
    }
  }

}
