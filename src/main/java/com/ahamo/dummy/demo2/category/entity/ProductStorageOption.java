package com.ahamo.dummy.demo2.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_storage_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ProductStorageOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id", length = 255)
  private String productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  @Column(name = "storage_capacity", nullable = false, length = 50)
  private String storageCapacity;
}
