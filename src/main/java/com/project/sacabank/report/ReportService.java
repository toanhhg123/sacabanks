package com.project.sacabank.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sacabank.auth.dto.UserRegisterDto;
import com.project.sacabank.enums.EnumNameRole;
import com.project.sacabank.formRegister.model.FormRegister;
import com.project.sacabank.formRegister.repository.FormRegisterRepository;
import com.project.sacabank.order.repository.OrderRepository;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.report.dto.ReportResponse;
import com.project.sacabank.repositories.CategoryRepository;
import com.project.sacabank.user.repository.UserRepository;

@Service
public class ReportService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  FormRegisterRepository formRegisterRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  OrderRepository orderRepository;

  public ReportResponse getReport() {

    var reportResponse = ReportResponse.builder()
        .categoryQuantity(categoryRepository.count())
        .userQuantity(userRepository.count())
        .productQuantity(productRepository.count())
        .vendorQuantity(userRepository.countByRoleName(EnumNameRole.VENDOR))
        .formRegisterQuantity(formRegisterRepository.count())
        .orderQuantity(orderRepository.count())
        .build();

    return reportResponse;
  }
}
