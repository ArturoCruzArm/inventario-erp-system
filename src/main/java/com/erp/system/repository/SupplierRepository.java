package com.erp.system.repository;

import com.erp.system.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    Optional<Supplier> findBySupplierCode(String supplierCode);
    
    List<Supplier> findByActiveTrue();
    
    Boolean existsBySupplierCode(String supplierCode);
    
    @Query("SELECT s FROM Supplier s WHERE s.active = true AND (s.supplierCode LIKE %:search% OR s.companyName LIKE %:search% OR s.contactPerson LIKE %:search% OR s.email LIKE %:search%)")
    List<Supplier> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT s FROM Supplier s WHERE s.active = true ORDER BY s.companyName")
    List<Supplier> findAllActiveOrderByCompanyName();
}