package com.ahamo.dummy.demo2.category.exception;

import static org.junit.jupiter.api.Assertions.*;

import com.ahamo.dummy.demo2.category.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CategoryExceptionHandlerTest {

  private CategoryExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new CategoryExceptionHandler();
  }

  @Test
  void testHandleCategoryNotFound() {
    CategoryNotFoundException exception = new CategoryNotFoundException("iphone");

    ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleCategoryNotFound(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().success());
    assertEquals("category_not_found", response.getBody().error().code());
    assertNotNull(response.getBody().error().traceId());
    assertTrue(response.getBody().error().message().contains("iphone"));
  }

  @Test
  void testHandleInvalidFilter() {
    InvalidFilterException exception = new InvalidFilterException("Invalid price range");

    ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleInvalidFilter(exception);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().success());
    assertEquals("invalid_filter", response.getBody().error().code());
    assertEquals("Invalid price range", response.getBody().error().message());
    assertNotNull(response.getBody().error().traceId());
  }

  @Test
  void testHandleGenericException() {
    Exception exception = new RuntimeException("Unexpected error");

    ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleGenericException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().success());
    assertEquals("internal_server_error", response.getBody().error().code());
    assertEquals("An unexpected error occurred", response.getBody().error().message());
    assertNotNull(response.getBody().error().traceId());
  }
}
