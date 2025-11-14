package com.ahamo.dummy.demo2.category.controller;

import com.ahamo.dummy.demo2.category.dto.ApiResponse;
import com.ahamo.dummy.demo2.category.dto.CategoryDetailResponse;
import com.ahamo.dummy.demo2.category.dto.CategoryListResponse;
import com.ahamo.dummy.demo2.category.dto.RecommendationResponse;
import com.ahamo.dummy.demo2.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<ApiResponse<CategoryListResponse>> getCategories() {
    log.info("Fetching all categories");
    CategoryListResponse response = categoryService.getAllCategories();
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/{categoryCode}")
  public ResponseEntity<ApiResponse<CategoryDetailResponse>> getCategoryProducts(
      @PathVariable String categoryCode,
      @RequestParam(required = false, defaultValue = "popularity") String sort,
      @RequestParam(required = false) Integer priceMin,
      @RequestParam(required = false) Integer priceMax,
      @RequestParam(required = false) String[] storage,
      @RequestParam(required = false) String[] color,
      @RequestParam(required = false) String campaignId) {

    log.info(
        "Fetching products for category: {} with filters - sort: {}, priceMin: {}, priceMax: {}, campaignId: {}",
        categoryCode,
        sort,
        priceMin,
        priceMax,
        campaignId);

    CategoryDetailResponse response =
        categoryService.getCategoryProducts(
            categoryCode, sort, priceMin, priceMax, storage, color, campaignId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/{categoryCode}/recommendations")
  public ResponseEntity<ApiResponse<RecommendationResponse>> getRecommendations(
      @PathVariable String categoryCode) {
    log.info("Fetching recommendations for category: {}", categoryCode);
    RecommendationResponse response = categoryService.getCategoryRecommendations(categoryCode);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
