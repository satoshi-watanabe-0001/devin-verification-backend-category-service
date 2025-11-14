package com.ahamo.dummy.demo2.category.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Table(name = "product_campaigns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ProductCampaign {

  @EmbeddedId private ProductCampaignId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "campaign_id", insertable = false, updatable = false)
  private Campaign campaign;

  @Column(name = "regular_price")
  private Integer regularPrice;

  @Column(name = "campaign_price")
  private Integer campaignPrice;

  @Column(name = "installment_months")
  private Integer installmentMonths;

  @Column(name = "installment_monthly_price")
  private Integer installmentMonthlyPrice;

  @Embeddable
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  @Builder
  @Getter
  @EqualsAndHashCode
  public static class ProductCampaignId implements Serializable {

    @Column(name = "product_id", length = 255)
    private String productId;

    @Column(name = "campaign_id", length = 50)
    private String campaignId;
  }
}
