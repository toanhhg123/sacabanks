package com.project.sacabank.exception;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;
  private List<String> validations;
}
