package com.project.sacabank.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.enums.EnumNameRole;
import com.project.sacabank.user.model.User;
import com.project.sacabank.web.home.dto.SupplierDto;

@Primary
@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.username = :nameOrEmail OR u.email = :nameOrEmail")
  Optional<User> findByUsernameOrEmail(@Param("nameOrEmail") String nameOrEmail);

  Optional<User> findByPhoneNumber(String PhoneNumber);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByPhoneNumber(String PhoneNumber);

  @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :userId")
  boolean existsByEmailAndNotUserId(@Param("email") String email, @Param("userId") UUID userId);

  @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.phoneNumber = :phone AND u.id != :userId")
  boolean existsByPhoneNumberAndNotUserId(@Param("phone") String phone, @Param("userId") UUID userId);

  @Query("SELECT COUNT(*) FROM User u WHERE u.role.name = :role")
  Long countByRoleName(@Param("role") EnumNameRole role);

}
