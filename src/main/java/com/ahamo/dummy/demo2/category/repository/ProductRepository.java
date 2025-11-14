package com.ahamo.dummy.demo2.category.repository;

import com.ahamo.dummy.demo2.category.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

  List<Product> findByCategoryCode(String categoryCode);

  long countByCategoryCode(String categoryCode);

  @Query(
      "SELECT DISTINCT p FROM Product p "
          + "LEFT JOIN FETCH p.storageOptions "
          + "LEFT JOIN FETCH p.colorOptions "
          + "LEFT JOIN FETCH p.productCampaigns pc "
          + "LEFT JOIN FETCH pc.campaign "
          + "WHERE p.categoryCode = :categoryCode")
  List<Product> findByCategoryCodeWithDetails(@Param("categoryCode") String categoryCode);

  @Query(
      "SELECT DISTINCT p FROM Product p "
          + "LEFT JOIN FETCH p.storageOptions "
          + "LEFT JOIN FETCH p.colorOptions "
          + "LEFT JOIN FETCH p.productCampaigns pc "
          + "LEFT JOIN FETCH pc.campaign c "
          + "WHERE p.categoryCode = :categoryCode "
          + "AND (:campaignId IS NULL OR c.campaignId = :campaignId)")
  List<Product> findByCategoryCodeAndCampaign(
      @Param("categoryCode") String categoryCode, @Param("campaignId") String campaignId);
}
