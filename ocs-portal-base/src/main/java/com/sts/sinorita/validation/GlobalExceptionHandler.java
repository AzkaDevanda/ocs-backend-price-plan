package com.sts.sinorita.validation;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.ApiResponse;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.utils.ValidationException;

import jakarta.persistence.PersistenceException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // 🔹 Handler untuk @Valid (otomatis)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    Map<String, String> errorDetails = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errorDetails.put(error.getField(), error.getDefaultMessage());
    }

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("status", 400);
    response.put("message", "Validation Failed");
    response.put("data", Map.of("error", errorDetails));

    return ResponseEntity.badRequest().body(response);
  }

  // 🔹 Handler untuk manual validation
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Map<String, Object>> handleCustomValidationException(ValidationException ex) {
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("status", 400);
    response.put("message", "Validation Failed");
    response.put("data", Map.of("error", ex.getErrors())); // Bisa Map<String, String> atau List<String>

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<CustomeResponse> handleResponseStatusException(ResponseStatusException e) {
    return ResponseEntity.status(e.getStatusCode()).body(new CustomeResponse(e.getStatusCode().value(), e.getReason(), null));
  }

  @ExceptionHandler({DataAccessException.class, PersistenceException.class, SQLException.class, TransactionException.class})
  public ResponseEntity<ApiResponse<String>> handleDatabaseException(Exception ex) {
    log.error("Database error occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatusConstant.INTERNAL_SERVER_ERROR_MESSAGE));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
    log.error("Unhandled error occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatusConstant.INTERNAL_SERVER_ERROR_MESSAGE));
  }
}
