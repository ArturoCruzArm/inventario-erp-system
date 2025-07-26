package com.erp.system.service;

import com.erp.system.entity.Customer;
import com.erp.system.entity.CustomerType;
import com.erp.system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findByActiveTrue();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findByCustomerCode(String customerCode) {
        return customerRepository.findByCustomerCode(customerCode);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer createCustomer(Customer customer) {
        if (existsByCustomerCode(customer.getCustomerCode())) {
            throw new RuntimeException("Customer code already exists: " + customer.getCustomerCode());
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        
        customer.setCompanyName(customerDetails.getCompanyName());
        customer.setContactPerson(customerDetails.getContactPerson());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setMobile(customerDetails.getMobile());
        customer.setFax(customerDetails.getFax());
        customer.setWebsite(customerDetails.getWebsite());
        customer.setTaxNumber(customerDetails.getTaxNumber());
        customer.setCustomerType(customerDetails.getCustomerType());
        customer.setCreditLimit(customerDetails.getCreditLimit());
        customer.setCreditDays(customerDetails.getCreditDays());
        
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customer.setActive(false);
        customerRepository.save(customer);
    }

    public boolean existsByCustomerCode(String customerCode) {
        return customerRepository.existsByCustomerCode(customerCode);
    }

    public List<Customer> searchCustomers(String searchTerm) {
        return customerRepository.findBySearchTerm(searchTerm);
    }

    public List<Customer> findByCustomerType(CustomerType customerType) {
        return customerRepository.findByCustomerType(customerType);
    }

    public List<Customer> findAllActiveOrderByCompanyName() {
        return customerRepository.findAllActiveOrderByCompanyName();
    }

    public String generateCustomerCode() {
        long count = customerRepository.count();
        return "CUST" + String.format("%06d", count + 1);
    }
}