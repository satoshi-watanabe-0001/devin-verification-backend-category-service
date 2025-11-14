package com.ahamo.dummy.demo2.category.repository;

import com.ahamo.dummy.demo2.category.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, String> {}
