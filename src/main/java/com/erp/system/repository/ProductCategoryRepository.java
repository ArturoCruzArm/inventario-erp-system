package com.erp.system.repository;

import com.erp.system.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    
    List<ProductCategory> findByParentIsNull();
    
    List<ProductCategory> findByParentId(Long parentId);
    
    List<ProductCategory> findByActiveTrue();
    
    @Query("SELECT pc FROM ProductCategory pc WHERE pc.active = true AND pc.name LIKE %:search%")
    List<ProductCategory> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT pc FROM ProductCategory pc WHERE pc.parent IS NULL AND pc.active = true ORDER BY pc.name")
    List<ProductCategory> findRootCategoriesOrderByName();
}