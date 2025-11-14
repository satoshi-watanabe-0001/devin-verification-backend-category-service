package com.ahamo.dummy.demo2.category.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ahamo.dummy.demo2.category.config.SecurityConfig;
import com.ahamo.dummy.demo2.category.dto.*;
import com.ahamo.dummy.demo2.category.exception.CategoryNotFoundException;
import com.ahamo.dummy.demo2.category.service.CategoryService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
@Import(SecurityConfig.class)
class CategoryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CategoryService categoryService;

  @Test
  void testGetCategories_Success() throws Exception {
    CategoryListResponse.CategoryInfo categoryInfo =
        new CategoryListResponse.CategoryInfo(
            "iphone", "iPhone", "image.jpg", 10L, "Latest iPhones");
    CategoryListResponse response = new CategoryListResponse(List.of(categoryInfo));

    when(categoryService.getAllCategories()).thenReturn(response);

    mockMvc
        .perform(get("/v1/products/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.categories[0].categoryCode").value("iphone"))
        .andExpect(jsonPath("$.data.categories[0].displayName").value("iPhone"))
        .andExpect(jsonPath("$.data.categories[0].productCount").value(10));
  }

  @Test
  void testGetCategoryProducts_Success() throws Exception {
    CategoryDetailResponse.CategoryInfo categoryInfo =
        new CategoryDetailResponse.CategoryInfo("iphone", "iPhone");
    ProductCardDto productCard =
        new ProductCardDto(
            "prod1",
            "Apple",
            "iPhone 15",
            "image.jpg",
            List.of("128GB", "256GB"),
            List.of(new ProductCardDto.ColorOption("Black", "#000000")),
            new PriceInfoDto(100000, 90000, 24, 3750),
            new AvailabilityDto("In Stock", "1-2 days"),
            Collections.emptyList());
    FilterOptionsDto filterOptions =
        new FilterOptionsDto(
            List.of("128GB", "256GB"),
            List.of("Black", "White"),
            new FilterOptionsDto.PriceRange(50000, 150000));
    CategoryDetailResponse response =
        new CategoryDetailResponse(categoryInfo, List.of(productCard), filterOptions);

    when(categoryService.getCategoryProducts(
            eq("iphone"), anyString(), any(), any(), any(), any(), any()))
        .thenReturn(response);

    mockMvc
        .perform(get("/v1/products/categories/iphone"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.category.categoryCode").value("iphone"))
        .andExpect(jsonPath("$.data.products[0].productId").value("prod1"))
        .andExpect(jsonPath("$.data.products[0].manufacturer").value("Apple"));
  }

  @Test
  void testGetCategoryProducts_WithFilters() throws Exception {
    CategoryDetailResponse.CategoryInfo categoryInfo =
        new CategoryDetailResponse.CategoryInfo("iphone", "iPhone");
    CategoryDetailResponse response =
        new CategoryDetailResponse(categoryInfo, Collections.emptyList(), null);

    when(categoryService.getCategoryProducts(
            eq("iphone"), eq("priceAsc"), eq(50000), eq(100000), any(), any(), eq("campaign1")))
        .thenReturn(response);

    mockMvc
        .perform(
            get("/v1/products/categories/iphone")
                .param("sort", "priceAsc")
                .param("priceMin", "50000")
                .param("priceMax", "100000")
                .param("campaignId", "campaign1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void testGetCategoryProducts_CategoryNotFound() throws Exception {
    when(categoryService.getCategoryProducts(
            eq("invalid"), anyString(), any(), any(), any(), any(), any()))
        .thenThrow(new CategoryNotFoundException("invalid"));

    mockMvc
        .perform(get("/v1/products/categories/invalid"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.error.code").value("category_not_found"));
  }

  @Test
  void testGetRecommendations_Success() throws Exception {
    ProductCardDto productCard =
        new ProductCardDto(
            "prod1",
            "Apple",
            "iPhone 15",
            "image.jpg",
            List.of("128GB"),
            Collections.emptyList(),
            null,
            null,
            Collections.emptyList());
    RecommendationResponse response =
        new RecommendationResponse(List.of(productCard), Collections.emptyList());

    when(categoryService.getCategoryRecommendations("iphone")).thenReturn(response);

    mockMvc
        .perform(get("/v1/products/categories/iphone/recommendations"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.featured[0].productId").value("prod1"));
  }

  @Test
  void testGetRecommendations_CategoryNotFound() throws Exception {
    when(categoryService.getCategoryRecommendations("invalid"))
        .thenThrow(new CategoryNotFoundException("invalid"));

    mockMvc
        .perform(get("/v1/products/categories/invalid/recommendations"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.error.code").value("category_not_found"));
  }
}
