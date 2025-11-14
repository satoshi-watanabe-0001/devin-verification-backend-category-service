package com.ahamo.dummy.demo2.category.client;

import com.ahamo.dummy.demo2.category.dto.AvailabilityDto;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class InventoryGraphQLClient {

  private final HttpGraphQlClient graphQlClient;
  private final Duration timeout;

  public InventoryGraphQLClient(
      @Value("${inventory-service.graphql.url}") String graphqlUrl,
      @Value("${inventory-service.graphql.timeout:5000}") long timeoutMs) {
    WebClient webClient = WebClient.builder().baseUrl(graphqlUrl).build();
    this.graphQlClient = HttpGraphQlClient.builder(webClient).build();
    this.timeout = Duration.ofMillis(timeoutMs);
  }

  public AvailabilityDto getProductAvailability(String productId) {
    try {
      String query =
          """
                  query GetProductAvailability($productId: String!) {
                      productAvailability(productId: $productId) {
                          onlineStock
                          deliveryLeadTime
                      }
                  }
                  """;

      AvailabilityResponse response =
          graphQlClient
              .document(query)
              .variable("productId", productId)
              .retrieve("productAvailability")
              .toEntity(AvailabilityResponse.class)
              .timeout(timeout)
              .onErrorResume(
                  e -> {
                    log.warn(
                        "Failed to fetch availability for product {}: {}",
                        productId,
                        e.getMessage());
                    return Mono.empty();
                  })
              .block();

      if (response == null) {
        return null;
      }

      return new AvailabilityDto(response.onlineStock(), response.deliveryLeadTime());
    } catch (Exception e) {
      log.error("Error fetching availability for product {}: {}", productId, e.getMessage());
      return null;
    }
  }

  private record AvailabilityResponse(String onlineStock, String deliveryLeadTime) {}
}
