package com.erp.system.repository;

import com.erp.system.entity.Payment;
import com.erp.system.entity.PaymentMethod;
import com.erp.system.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByPaymentNumber(String paymentNumber);
    
    List<Payment> findByCustomerId(Long customerId);
    
    List<Payment> findByInvoiceId(Long invoiceId);
    
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
    
    List<Payment> findByStatus(PaymentStatus status);
    
    List<Payment> findByActiveTrue();
    
    Boolean existsByPaymentNumber(String paymentNumber);
    
    @Query("SELECT p FROM Payment p WHERE p.active = true AND (p.paymentNumber LIKE %:search% OR p.customer.companyName LIKE %:search% OR p.referenceNumber LIKE %:search%)")
    List<Payment> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate AND p.active = true ORDER BY p.paymentDate DESC")
    List<Payment> findByPaymentDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT p FROM Payment p WHERE p.active = true ORDER BY p.paymentDate DESC")
    List<Payment> findAllActiveOrderByPaymentDateDesc();
    
    @Query("SELECT p FROM Payment p WHERE p.active = true ORDER BY p.paymentDate DESC")
    List<Payment> findTop10ByOrderByPaymentDateDesc();
}