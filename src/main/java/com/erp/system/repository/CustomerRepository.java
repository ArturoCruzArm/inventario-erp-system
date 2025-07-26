package com.erp.system.repository;

import com.erp.system.entity.Customer;
import com.erp.system.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByCustomerCode(String customerCode);
    
    List<Customer> findByCustomerType(CustomerType customerType);
    
    List<Customer> findByActiveTrue();
    
    Boolean existsByCustomerCode(String customerCode);
    
    @Query("SELECT c FROM Customer c WHERE c.active = true AND (c.customerCode LIKE %:search% OR c.companyName LIKE %:search% OR c.contactPerson LIKE %:search% OR c.email LIKE %:search%)")
    List<Customer> findBySearchTerm(@Param("search") String search);
    
    @Query("SELECT c FROM Customer c WHERE c.active = true ORDER BY c.companyName")
    List<Customer> findAllActiveOrderByCompanyName();
}