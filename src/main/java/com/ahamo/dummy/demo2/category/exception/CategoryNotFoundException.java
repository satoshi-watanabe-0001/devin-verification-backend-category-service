package com.ahamo.dummy.demo2.category.exception;

public class CategoryNotFoundException extends RuntimeException {

  private final String categoryCode;

  public CategoryNotFoundException(String categoryCode) {
    super("Category not found: " + categoryCode);
    this.categoryCode = categoryCode;
  }

  public String getCategoryCode() {
    return categoryCode;
  }
}
