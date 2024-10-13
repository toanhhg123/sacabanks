package com.project.sacabank.formRegister.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.project.sacabank.base.BaseRepository;
import com.project.sacabank.formRegister.model.FormRegister;

public interface FormRegisterRepository extends BaseRepository<FormRegister, UUID> {

    @Query("SELECT COUNT(u) > 0 FROM FormRegister u WHERE u.email = :email OR u.phone = :phone")
    Boolean existsByPhoneOrEmail(String phone, String email);
}
