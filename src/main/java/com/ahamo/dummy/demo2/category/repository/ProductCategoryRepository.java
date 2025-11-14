package com.ahamo.dummy.demo2.category.repository;

import com.ahamo.dummy.demo2.category.entity.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

  List<ProductCategory> findAllByOrderByDisplayOrderAsc();
}
