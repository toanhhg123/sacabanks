package com.project.sacabank.Role.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sacabank.Role.model.Role;
import com.project.sacabank.enums.EnumNameRole;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByName(EnumNameRole name);

}
