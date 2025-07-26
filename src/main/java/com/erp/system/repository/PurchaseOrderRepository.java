package com.erp.system.repository;

import com.erp.system.entity.OrderStatus;
import com.erp.system.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    Optional<PurchaseOrder> findByOrderNumber(String orderNumber);
    
    List<PurchaseOrder> findBySupplierId(Long supplierId);
    
    List<PurchaseOrder> findByStatus(OrderStatus status);
    
    List<PurchaseOrder> findByActiveTrue();
    
    Boolean existsByOrderNumber(String orderNumber);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.active = true AND (po.orderNumber LIKE %:search% OR po.supplier.companyName LIKE %:search%)")
    List<PurchaseOrder> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.orderDate BETWEEN :startDate AND :endDate AND po.active = true ORDER BY po.orderDate DESC")
    List<PurchaseOrder> findByOrderDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.active = true ORDER BY po.orderDate DESC")
    List<PurchaseOrder> findAllActiveOrderByOrderDateDesc();
}