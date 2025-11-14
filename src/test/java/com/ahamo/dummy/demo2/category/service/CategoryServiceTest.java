package com.ahamo.dummy.demo2.category.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ahamo.dummy.demo2.category.client.InventoryGraphQLClient;
import com.ahamo.dummy.demo2.category.dto.*;
import com.ahamo.dummy.demo2.category.entity.*;
import com.ahamo.dummy.demo2.category.exception.CategoryNotFoundException;
import com.ahamo.dummy.demo2.category.repository.ProductCategoryRepository;
import com.ahamo.dummy.demo2.category.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @Mock private ProductCategoryRepository categoryRepository;

  @Mock private ProductRepository productRepository;

  @Mock private InventoryGraphQLClient inventoryClient;

  @InjectMocks private CategoryService categoryService;

  private ProductCategory testCategory;
  private Product testProduct;

  @BeforeEach
  void setUp() {
    testCategory =
        ProductCategory.builder()
            .categoryCode("iphone")
            .displayName("iPhone")
            .heroImageUrl("image.jpg")
            .displayOrder(1)
            .leadText("Latest iPhones")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    testProduct =
        Product.builder()
            .productId("prod1")
            .categoryCode("iphone")
            .manufacturer("Apple")
            .modelName("iPhone 15")
            .imageUrl("image.jpg")
            .description("Latest iPhone")
            .storageOptions(new ArrayList<>())
            .colorOptions(new ArrayList<>())
            .productCampaigns(new ArrayList<>())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testGetAllCategories_Success() {
    when(categoryRepository.findAllByOrderByDisplayOrderAsc()).thenReturn(List.of(testCategory));
    when(productRepository.countByCategoryCode("iphone")).thenReturn(10L);

    CategoryListResponse response = categoryService.getAllCategories();

    assertNotNull(response);
    assertEquals(1, response.categories().size());
    assertEquals("iphone", response.categories().get(0).categoryCode());
    assertEquals("iPhone", response.categories().get(0).displayName());
    assertEquals(10L, response.categories().get(0).productCount());
  }

  @Test
  void testGetAllCategories_EmptyList() {
    when(categoryRepository.findAllByOrderByDisplayOrderAsc()).thenReturn(Collections.emptyList());

    CategoryListResponse response = categoryService.getAllCategories();

    assertNotNull(response);
    assertTrue(response.categories().isEmpty());
  }

  @Test
  void testGetCategoryProducts_Success() {
    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeWithDetails("iphone"))
        .thenReturn(List.of(testProduct));
    when(inventoryClient.getProductAvailability(any()))
        .thenReturn(new AvailabilityDto("In Stock", "1-2 days"));

    CategoryDetailResponse response =
        categoryService.getCategoryProducts("iphone", "popularity", null, null, null, null, null);

    assertNotNull(response);
    assertEquals("iphone", response.category().categoryCode());
    assertEquals("iPhone", response.category().displayName());
    assertEquals(1, response.products().size());
    assertEquals("prod1", response.products().get(0).productId());
  }

  @Test
  void testGetCategoryProducts_CategoryNotFound() {
    when(categoryRepository.findById("invalid")).thenReturn(Optional.empty());

    assertThrows(
        CategoryNotFoundException.class,
        () ->
            categoryService.getCategoryProducts(
                "invalid", "popularity", null, null, null, null, null));
  }

  @Test
  void testGetCategoryProducts_WithCampaignFilter() {
    Campaign campaign =
        Campaign.builder()
            .campaignId("campaign1")
            .campaignName("Summer Sale")
            .badgeLabel("SALE")
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusDays(30))
            .createdAt(LocalDateTime.now())
            .build();

    ProductCampaign productCampaign =
        ProductCampaign.builder()
            .id(
                ProductCampaign.ProductCampaignId.builder()
                    .productId("prod1")
                    .campaignId("campaign1")
                    .build())
            .campaign(campaign)
            .regularPrice(100000)
            .campaignPrice(90000)
            .installmentMonths(24)
            .installmentMonthlyPrice(3750)
            .build();

    testProduct.getProductCampaigns().add(productCampaign);

    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeAndCampaign("iphone", "campaign1"))
        .thenReturn(List.of(testProduct));

    CategoryDetailResponse response =
        categoryService.getCategoryProducts(
            "iphone", "popularity", null, null, null, null, "campaign1");

    assertNotNull(response);
    assertEquals(1, response.products().size());
    assertNotNull(response.products().get(0).priceInfo());
    assertEquals(90000, response.products().get(0).priceInfo().campaignPrice());
  }

  @Test
  void testGetCategoryProducts_WithPriceFilter() {
    Campaign campaign =
        Campaign.builder()
            .campaignId("campaign1")
            .campaignName("Sale")
            .badgeLabel("SALE")
            .createdAt(LocalDateTime.now())
            .build();

    ProductCampaign productCampaign =
        ProductCampaign.builder()
            .id(
                ProductCampaign.ProductCampaignId.builder()
                    .productId("prod1")
                    .campaignId("campaign1")
                    .build())
            .campaign(campaign)
            .regularPrice(100000)
            .campaignPrice(90000)
            .installmentMonths(24)
            .installmentMonthlyPrice(3750)
            .build();

    testProduct.getProductCampaigns().add(productCampaign);

    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeWithDetails("iphone"))
        .thenReturn(List.of(testProduct));

    CategoryDetailResponse response =
        categoryService.getCategoryProducts("iphone", "popularity", 80000, 95000, null, null, null);

    assertNotNull(response);
    assertEquals(1, response.products().size());
  }

  @Test
  void testGetCategoryProducts_PriceFilterExcludesProduct() {
    Campaign campaign =
        Campaign.builder()
            .campaignId("campaign1")
            .campaignName("Sale")
            .badgeLabel("SALE")
            .createdAt(LocalDateTime.now())
            .build();

    ProductCampaign productCampaign =
        ProductCampaign.builder()
            .id(
                ProductCampaign.ProductCampaignId.builder()
                    .productId("prod1")
                    .campaignId("campaign1")
                    .build())
            .campaign(campaign)
            .regularPrice(100000)
            .campaignPrice(90000)
            .installmentMonths(24)
            .installmentMonthlyPrice(3750)
            .build();

    testProduct.getProductCampaigns().add(productCampaign);

    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeWithDetails("iphone"))
        .thenReturn(List.of(testProduct));

    CategoryDetailResponse response =
        categoryService.getCategoryProducts(
            "iphone", "popularity", 100000, 150000, null, null, null);

    assertNotNull(response);
    assertEquals(0, response.products().size());
  }

  @Test
  void testGetCategoryProducts_SortByPriceAsc() {
    Product product2 =
        Product.builder()
            .productId("prod2")
            .categoryCode("iphone")
            .manufacturer("Apple")
            .modelName("iPhone 14")
            .imageUrl("image2.jpg")
            .storageOptions(new ArrayList<>())
            .colorOptions(new ArrayList<>())
            .productCampaigns(new ArrayList<>())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Campaign campaign1 =
        Campaign.builder()
            .campaignId("c1")
            .campaignName("Sale")
            .createdAt(LocalDateTime.now())
            .build();
    Campaign campaign2 =
        Campaign.builder()
            .campaignId("c2")
            .campaignName("Sale")
            .createdAt(LocalDateTime.now())
            .build();

    ProductCampaign pc1 =
        ProductCampaign.builder()
            .id(
                ProductCampaign.ProductCampaignId.builder()
                    .productId("prod1")
                    .campaignId("c1")
                    .build())
            .campaign(campaign1)
            .regularPrice(100000)
            .campaignPrice(90000)
            .build();

    ProductCampaign pc2 =
        ProductCampaign.builder()
            .id(
                ProductCampaign.ProductCampaignId.builder()
                    .productId("prod2")
                    .campaignId("c2")
                    .build())
            .campaign(campaign2)
            .regularPrice(80000)
            .campaignPrice(70000)
            .build();

    testProduct.getProductCampaigns().add(pc1);
    product2.getProductCampaigns().add(pc2);

    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeWithDetails("iphone"))
        .thenReturn(List.of(testProduct, product2));

    CategoryDetailResponse response =
        categoryService.getCategoryProducts("iphone", "priceAsc", null, null, null, null, null);

    assertNotNull(response);
    assertEquals(2, response.products().size());
    assertEquals("prod2", response.products().get(0).productId());
    assertEquals("prod1", response.products().get(1).productId());
  }

  @Test
  void testGetCategoryRecommendations_Success() {
    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeWithDetails("iphone"))
        .thenReturn(List.of(testProduct));

    RecommendationResponse response = categoryService.getCategoryRecommendations("iphone");

    assertNotNull(response);
    assertEquals(1, response.featured().size());
    assertEquals("prod1", response.featured().get(0).productId());
    assertTrue(response.bundles().isEmpty());
  }

  @Test
  void testGetCategoryRecommendations_CategoryNotFound() {
    when(categoryRepository.findById("invalid")).thenReturn(Optional.empty());

    assertThrows(
        CategoryNotFoundException.class,
        () -> categoryService.getCategoryRecommendations("invalid"));
  }

  @Test
  void testGetCategoryProducts_InventoryServiceUnavailable() {
    when(categoryRepository.findById("iphone")).thenReturn(Optional.of(testCategory));
    when(productRepository.findByCategoryCodeWithDetails("iphone"))
        .thenReturn(List.of(testProduct));
    when(inventoryClient.getProductAvailability(any())).thenReturn(null);

    CategoryDetailResponse response =
        categoryService.getCategoryProducts("iphone", "popularity", null, null, null, null, null);

    assertNotNull(response);
    assertEquals(1, response.products().size());
    assertNull(response.products().get(0).availability());
  }
}
