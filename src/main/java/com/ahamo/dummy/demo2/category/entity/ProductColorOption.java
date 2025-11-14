package com.ahamo.dummy.demo2.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_color_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ProductColorOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id", length = 255)
  private String productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  @Column(name = "color_name", nullable = false, length = 100)
  private String colorName;

  @Column(name = "color_code", nullable = false, length = 7)
  private String colorCode;
}
