package com.project.sacabank.Role.model;

import com.project.sacabank.base.BaseModel;
import com.project.sacabank.enums.EnumNameRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(indexes = { @Index(name = "NAME_ROLE_INDEX", columnList = "name", unique = true) })
public class Role extends BaseModel {

  @NotNull
  @Enumerated(EnumType.STRING)
  EnumNameRole name;
}
