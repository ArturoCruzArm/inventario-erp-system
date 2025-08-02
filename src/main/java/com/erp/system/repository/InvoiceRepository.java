package com.erp.system.repository;

import com.erp.system.entity.Invoice;
import com.erp.system.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    
    List<Invoice> findByCustomerId(Long customerId);
    
    List<Invoice> findByStatus(InvoiceStatus status);
    
    List<Invoice> findByActiveTrue();
    
    Boolean existsByInvoiceNumber(String invoiceNumber);
    
    @Query("SELECT i FROM Invoice i WHERE i.active = true AND (i.invoiceNumber LIKE %:search% OR i.customer.companyName LIKE %:search%)")
    List<Invoice> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT i FROM Invoice i WHERE i.invoiceDate BETWEEN :startDate AND :endDate AND i.active = true ORDER BY i.invoiceDate DESC")
    List<Invoice> findByInvoiceDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT i FROM Invoice i WHERE i.dueDate < :date AND i.status != 'PAID' AND i.active = true")
    List<Invoice> findOverdueInvoices(@Param("date") LocalDate date);
    
    @Query("SELECT i FROM Invoice i WHERE i.active = true ORDER BY i.invoiceDate DESC")
    List<Invoice> findAllActiveOrderByInvoiceDateDesc();
    
    @Query("SELECT i FROM Invoice i WHERE i.active = true ORDER BY i.invoiceDate DESC")
    List<Invoice> findTop10ByOrderByCreatedAtDesc();
    
    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE i.active = true AND i.status = 'PAID'")
    BigDecimal getTotalRevenue();
    
    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE i.active = true AND i.status = 'PENDING'")
    BigDecimal getPendingAmount();
}