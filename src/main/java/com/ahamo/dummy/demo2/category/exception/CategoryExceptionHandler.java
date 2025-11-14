package com.ahamo.dummy.demo2.category.exception;

import com.ahamo.dummy.demo2.category.dto.ApiResponse;
import com.ahamo.dummy.demo2.category.dto.ApiResponse.ErrorInfo;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CategoryExceptionHandler {

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleCategoryNotFound(CategoryNotFoundException ex) {
    String traceId = UUID.randomUUID().toString();
    log.error("Category not found - traceId: {}, category: {}", traceId, ex.getCategoryCode());

    ErrorInfo error =
        new ErrorInfo(
            "category_not_found",
            ex.getMessage(),
            traceId,
            Collections.singletonList("Category code: " + ex.getCategoryCode()));

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(error));
  }

  @ExceptionHandler(InvalidFilterException.class)
  public ResponseEntity<ApiResponse<Void>> handleInvalidFilter(InvalidFilterException ex) {
    String traceId = UUID.randomUUID().toString();
    log.error("Invalid filter - traceId: {}, message: {}", traceId, ex.getMessage());

    ErrorInfo error =
        new ErrorInfo("invalid_filter", ex.getMessage(), traceId, Collections.emptyList());

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.error(error));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    String traceId = UUID.randomUUID().toString();
    log.error("Unexpected error - traceId: {}", traceId, ex);

    ErrorInfo error =
        new ErrorInfo(
            "internal_server_error",
            "An unexpected error occurred",
            traceId,
            Collections.emptyList());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(error));
  }
}
