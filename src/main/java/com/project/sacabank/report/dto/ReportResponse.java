package com.project.sacabank.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
  Long userQuantity;
  Long productQuantity;
  Long vendorQuantity;
  Long formRegisterQuantity;
  Long categoryQuantity;
  Long orderQuantity;
}
