package com.ahamo.dummy.demo2.category.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "campaigns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Campaign {

  @Id
  @Column(name = "campaign_id", length = 50)
  private String campaignId;

  @Column(name = "campaign_name", nullable = false, length = 100)
  private String campaignName;

  @Column(name = "badge_label", length = 50)
  private String badgeLabel;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void prePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
