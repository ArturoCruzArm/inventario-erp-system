package com.erp.system.repository;

import com.erp.system.entity.OrderStatus;
import com.erp.system.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    
    Optional<SalesOrder> findByOrderNumber(String orderNumber);
    
    List<SalesOrder> findByCustomerId(Long customerId);
    
    List<SalesOrder> findByStatus(OrderStatus status);
    
    List<SalesOrder> findByActiveTrue();
    
    Boolean existsByOrderNumber(String orderNumber);
    
    @Query("SELECT so FROM SalesOrder so WHERE so.active = true AND (so.orderNumber LIKE %:search% OR so.customer.companyName LIKE %:search%)")
    List<SalesOrder> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT so FROM SalesOrder so WHERE so.orderDate BETWEEN :startDate AND :endDate AND so.active = true ORDER BY so.orderDate DESC")
    List<SalesOrder> findByOrderDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT so FROM SalesOrder so WHERE so.active = true ORDER BY so.orderDate DESC")
    List<SalesOrder> findAllActiveOrderByOrderDateDesc();
}