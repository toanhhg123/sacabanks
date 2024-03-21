package com.project.sacabank.exception;

public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CustomException(String msg) {
    super(msg);
  }

  public static void throwError(String msg) {
    throw new CustomException(msg);
  }

}