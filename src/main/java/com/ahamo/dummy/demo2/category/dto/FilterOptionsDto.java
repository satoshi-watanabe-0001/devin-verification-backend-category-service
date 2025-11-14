package com.ahamo.dummy.demo2.category.dto;

import java.util.List;

public record FilterOptionsDto(
    List<String> storageOptions, List<String> colorOptions, PriceRange priceRange) {

  public record PriceRange(Integer min, Integer max) {}
}
