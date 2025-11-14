package com.ahamo.dummy.demo2.category.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Product {

  @Id
  @Column(name = "product_id", length = 255)
  private String productId;

  @Column(name = "category_code", length = 50)
  private String categoryCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_code", insertable = false, updatable = false)
  private ProductCategory category;

  @Column(name = "manufacturer", nullable = false, length = 100)
  private String manufacturer;

  @Column(name = "model_name", nullable = false, length = 255)
  private String modelName;

  @Column(name = "image_url", columnDefinition = "TEXT")
  private String imageUrl;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ProductStorageOption> storageOptions = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ProductColorOption> colorOptions = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ProductCampaign> productCampaigns = new ArrayList<>();

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
