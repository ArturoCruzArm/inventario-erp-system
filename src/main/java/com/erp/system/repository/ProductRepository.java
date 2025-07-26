package com.erp.system.repository;

import com.erp.system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByProductCode(String productCode);
    
    Optional<Product> findByBarcode(String barcode);
    
    List<Product> findByCategoryId(Long categoryId);
    
    List<Product> findByActiveTrue();
    
    Boolean existsByProductCode(String productCode);
    
    Boolean existsByBarcode(String barcode);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND (p.productCode LIKE %:search% OR p.name LIKE %:search% OR p.description LIKE %:search%)")
    List<Product> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT p FROM Product p WHERE p.active = true ORDER BY p.name")
    List<Product> findAllActiveOrderByName();
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.category.id = :categoryId ORDER BY p.name")
    List<Product> findByCategoryIdOrderByName(@Param("categoryId") Long categoryId);
}