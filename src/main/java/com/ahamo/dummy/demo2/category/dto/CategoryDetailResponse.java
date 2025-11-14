package com.ahamo.dummy.demo2.category.dto;

import java.util.List;

public record CategoryDetailResponse(
    CategoryInfo category, List<ProductCardDto> products, FilterOptionsDto filterOptions) {

  public record CategoryInfo(String categoryCode, String displayName) {}
}
