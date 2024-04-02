package com.project.sacabank.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false), null);

    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorMessage> CustomException(CustomException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.CONFLICT.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false), null);
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException e) {

    var validations = e.getBindingResult().getFieldErrors().stream().map((error) -> error.getDefaultMessage()).toList();

    ErrorMessage message = ErrorMessage.builder()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .timestamp(new Date())
        .description(null)
        .validations(validations)
        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .message(null)
        .build();

    return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorMessage> handleAuthenticationException(Exception ex, WebRequest request) {

    ErrorMessage message = new ErrorMessage(
        HttpStatus.UNAUTHORIZED.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false), null);

    return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false), null);
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}