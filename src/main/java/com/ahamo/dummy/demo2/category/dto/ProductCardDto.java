package com.ahamo.dummy.demo2.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductCardDto(
    String productId,
    String manufacturer,
    String modelName,
    String imageUrl,
    List<String> storageOptions,
    List<ColorOption> colorOptions,
    PriceInfoDto priceInfo,
    AvailabilityDto availability,
    List<CampaignBadge> campaignBadges) {

  public record ColorOption(String name, String code) {}

  public record CampaignBadge(String label, String campaignId) {}
}
