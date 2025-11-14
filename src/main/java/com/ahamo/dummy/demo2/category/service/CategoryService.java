package com.ahamo.dummy.demo2.category.service;

import com.ahamo.dummy.demo2.category.client.InventoryGraphQLClient;
import com.ahamo.dummy.demo2.category.dto.*;
import com.ahamo.dummy.demo2.category.entity.*;
import com.ahamo.dummy.demo2.category.exception.CategoryNotFoundException;
import com.ahamo.dummy.demo2.category.repository.ProductCategoryRepository;
import com.ahamo.dummy.demo2.category.repository.ProductRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

  private final ProductCategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final InventoryGraphQLClient inventoryClient;

  @Transactional(readOnly = true)
  public CategoryListResponse getAllCategories() {
    List<ProductCategory> categories = categoryRepository.findAllByOrderByDisplayOrderAsc();

    List<CategoryListResponse.CategoryInfo> categoryInfos =
        categories.stream()
            .map(
                category -> {
                  long productCount =
                      productRepository.countByCategoryCode(category.getCategoryCode());
                  return new CategoryListResponse.CategoryInfo(
                      category.getCategoryCode(),
                      category.getDisplayName(),
                      category.getHeroImageUrl(),
                      productCount,
                      category.getLeadText());
                })
            .toList();

    return new CategoryListResponse(categoryInfos);
  }

  @Transactional(readOnly = true)
  public CategoryDetailResponse getCategoryProducts(
      String categoryCode,
      String sort,
      Integer priceMin,
      Integer priceMax,
      String[] storage,
      String[] color,
      String campaignId) {

    ProductCategory category =
        categoryRepository
            .findById(categoryCode)
            .orElseThrow(() -> new CategoryNotFoundException(categoryCode));

    List<Product> products;
    if (campaignId != null) {
      products = productRepository.findByCategoryCodeAndCampaign(categoryCode, campaignId);
    } else {
      products = productRepository.findByCategoryCodeWithDetails(categoryCode);
    }

    List<ProductCardDto> productCards =
        products.stream()
            .map(this::convertToProductCard)
            .filter(card -> applyFilters(card, priceMin, priceMax, storage, color))
            .sorted(getComparator(sort))
            .toList();

    FilterOptionsDto filterOptions = buildFilterOptions(products);

    CategoryDetailResponse.CategoryInfo categoryInfo =
        new CategoryDetailResponse.CategoryInfo(
            category.getCategoryCode(), category.getDisplayName());

    return new CategoryDetailResponse(categoryInfo, productCards, filterOptions);
  }

  @Transactional(readOnly = true)
  public RecommendationResponse getCategoryRecommendations(String categoryCode) {
    categoryRepository
        .findById(categoryCode)
        .orElseThrow(() -> new CategoryNotFoundException(categoryCode));

    List<Product> products = productRepository.findByCategoryCodeWithDetails(categoryCode);

    List<ProductCardDto> featured =
        products.stream().limit(5).map(this::convertToProductCard).toList();

    List<RecommendationResponse.BundleInfo> bundles = Collections.emptyList();

    return new RecommendationResponse(featured, bundles);
  }

  private ProductCardDto convertToProductCard(Product product) {
    List<String> storageOptions =
        product.getStorageOptions().stream()
            .map(ProductStorageOption::getStorageCapacity)
            .distinct()
            .toList();

    List<ProductCardDto.ColorOption> colorOptions =
        product.getColorOptions().stream()
            .map(
                option ->
                    new ProductCardDto.ColorOption(option.getColorName(), option.getColorCode()))
            .distinct()
            .toList();

    PriceInfoDto priceInfo = null;
    List<ProductCardDto.CampaignBadge> campaignBadges = new ArrayList<>();

    if (!product.getProductCampaigns().isEmpty()) {
      ProductCampaign firstCampaign = product.getProductCampaigns().get(0);
      priceInfo =
          new PriceInfoDto(
              firstCampaign.getRegularPrice(),
              firstCampaign.getCampaignPrice(),
              firstCampaign.getInstallmentMonths(),
              firstCampaign.getInstallmentMonthlyPrice());

      campaignBadges =
          product.getProductCampaigns().stream()
              .map(
                  pc ->
                      new ProductCardDto.CampaignBadge(
                          pc.getCampaign().getBadgeLabel(), pc.getCampaign().getCampaignId()))
              .filter(badge -> badge.label() != null)
              .toList();
    }

    AvailabilityDto availability = null;
    try {
      availability = inventoryClient.getProductAvailability(product.getProductId());
    } catch (Exception e) {
      log.warn("Failed to fetch availability for product {}", product.getProductId());
    }

    return new ProductCardDto(
        product.getProductId(),
        product.getManufacturer(),
        product.getModelName(),
        product.getImageUrl(),
        storageOptions,
        colorOptions,
        priceInfo,
        availability,
        campaignBadges);
  }

  private boolean applyFilters(
      ProductCardDto card, Integer priceMin, Integer priceMax, String[] storage, String[] color) {

    if (priceMin != null && card.priceInfo() != null) {
      Integer price =
          card.priceInfo().campaignPrice() != null
              ? card.priceInfo().campaignPrice()
              : card.priceInfo().regularPrice();
      if (price != null && price < priceMin) {
        return false;
      }
    }

    if (priceMax != null && card.priceInfo() != null) {
      Integer price =
          card.priceInfo().campaignPrice() != null
              ? card.priceInfo().campaignPrice()
              : card.priceInfo().regularPrice();
      if (price != null && price > priceMax) {
        return false;
      }
    }

    if (storage != null && storage.length > 0) {
      Set<String> storageSet = Set.of(storage);
      boolean hasMatchingStorage = card.storageOptions().stream().anyMatch(storageSet::contains);
      if (!hasMatchingStorage) {
        return false;
      }
    }

    if (color != null && color.length > 0) {
      Set<String> colorSet = Set.of(color);
      boolean hasMatchingColor =
          card.colorOptions().stream()
              .map(ProductCardDto.ColorOption::name)
              .anyMatch(colorSet::contains);
      if (!hasMatchingColor) {
        return false;
      }
    }

    return true;
  }

  private Comparator<ProductCardDto> getComparator(String sort) {
    return switch (sort) {
      case "priceAsc" ->
          Comparator.<ProductCardDto, Integer>comparing(
              card -> {
                if (card.priceInfo() == null) return Integer.MAX_VALUE;
                Integer price =
                    card.priceInfo().campaignPrice() != null
                        ? card.priceInfo().campaignPrice()
                        : card.priceInfo().regularPrice();
                return price != null ? price : Integer.MAX_VALUE;
              });
      case "priceDesc" ->
          Comparator.<ProductCardDto, Integer>comparing(
                  card -> {
                    if (card.priceInfo() == null) return Integer.MIN_VALUE;
                    Integer price =
                        card.priceInfo().campaignPrice() != null
                            ? card.priceInfo().campaignPrice()
                            : card.priceInfo().regularPrice();
                    return price != null ? price : Integer.MIN_VALUE;
                  })
              .reversed();
      case "newArrival" -> Comparator.comparing(ProductCardDto::productId).reversed();
      default -> Comparator.comparing(ProductCardDto::productId);
    };
  }

  private FilterOptionsDto buildFilterOptions(List<Product> products) {
    Set<String> storageOptions =
        products.stream()
            .flatMap(p -> p.getStorageOptions().stream())
            .map(ProductStorageOption::getStorageCapacity)
            .collect(Collectors.toSet());

    Set<String> colorOptions =
        products.stream()
            .flatMap(p -> p.getColorOptions().stream())
            .map(ProductColorOption::getColorName)
            .collect(Collectors.toSet());

    Integer minPrice = null;
    Integer maxPrice = null;

    for (Product product : products) {
      for (ProductCampaign pc : product.getProductCampaigns()) {
        Integer price =
            pc.getCampaignPrice() != null ? pc.getCampaignPrice() : pc.getRegularPrice();
        if (price != null) {
          if (minPrice == null || price < minPrice) {
            minPrice = price;
          }
          if (maxPrice == null || price > maxPrice) {
            maxPrice = price;
          }
        }
      }
    }

    FilterOptionsDto.PriceRange priceRange = new FilterOptionsDto.PriceRange(minPrice, maxPrice);

    return new FilterOptionsDto(
        new ArrayList<>(storageOptions), new ArrayList<>(colorOptions), priceRange);
  }
}
