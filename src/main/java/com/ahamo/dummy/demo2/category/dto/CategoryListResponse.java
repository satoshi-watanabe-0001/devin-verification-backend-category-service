package com.ahamo.dummy.demo2.category.dto;

import java.util.List;

public record CategoryListResponse(List<CategoryInfo> categories) {

  public record CategoryInfo(
      String categoryCode,
      String displayName,
      String heroImageUrl,
      Long productCount,
      String leadText) {}
}
