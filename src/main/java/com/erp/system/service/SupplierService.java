package com.erp.system.service;

import com.erp.system.entity.Supplier;
import com.erp.system.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> findAll() {
        return supplierRepository.findByActiveTrue();
    }

    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> findBySupplierCode(String supplierCode) {
        return supplierRepository.findBySupplierCode(supplierCode);
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier createSupplier(Supplier supplier) {
        if (existsBySupplierCode(supplier.getSupplierCode())) {
            throw new RuntimeException("Supplier code already exists: " + supplier.getSupplierCode());
        }
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        
        supplier.setCompanyName(supplierDetails.getCompanyName());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setPhone(supplierDetails.getPhone());
        supplier.setMobile(supplierDetails.getMobile());
        supplier.setFax(supplierDetails.getFax());
        supplier.setWebsite(supplierDetails.getWebsite());
        supplier.setTaxNumber(supplierDetails.getTaxNumber());
        supplier.setPaymentTerms(supplierDetails.getPaymentTerms());
        supplier.setNotes(supplierDetails.getNotes());
        
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        supplier.setActive(false);
        supplierRepository.save(supplier);
    }

    public boolean existsBySupplierCode(String supplierCode) {
        return supplierRepository.existsBySupplierCode(supplierCode);
    }

    public List<Supplier> searchSuppliers(String searchTerm) {
        return supplierRepository.findBySearchTerm(searchTerm);
    }

    public List<Supplier> findAllActiveOrderByCompanyName() {
        return supplierRepository.findAllActiveOrderByCompanyName();
    }

    public String generateSupplierCode() {
        long count = supplierRepository.count();
        return "SUPP" + String.format("%06d", count + 1);
    }

    public void deleteById(Long id) {
        deleteSupplier(id);
    }
}