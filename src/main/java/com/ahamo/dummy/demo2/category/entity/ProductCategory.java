package com.ahamo.dummy.demo2.category.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "product_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ProductCategory {

  @Id
  @Column(name = "category_code", length = 50)
  private String categoryCode;

  @Column(name = "display_name", nullable = false, length = 100)
  private String displayName;

  @Column(name = "hero_image_url", columnDefinition = "TEXT")
  private String heroImageUrl;

  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;

  @Column(name = "lead_text", columnDefinition = "TEXT")
  private String leadText;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
