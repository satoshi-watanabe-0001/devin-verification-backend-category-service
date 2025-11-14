package com.ahamo.dummy.demo2.category.dto;

import java.util.List;

public record RecommendationResponse(List<ProductCardDto> featured, List<BundleInfo> bundles) {

  public record BundleInfo(
      String bundleId, String bundleName, List<ProductCardDto> products, Integer totalPrice) {}
}
