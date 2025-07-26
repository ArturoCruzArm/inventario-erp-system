package com.erp.system.service;

import com.erp.system.entity.Product;
import com.erp.system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findByActiveTrue();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByProductCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    public Optional<Product> findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product createProduct(Product product) {
        if (existsByProductCode(product.getProductCode())) {
            throw new RuntimeException("Product code already exists: " + product.getProductCode());
        }
        if (product.getBarcode() != null && !product.getBarcode().isEmpty() && existsByBarcode(product.getBarcode())) {
            throw new RuntimeException("Barcode already exists: " + product.getBarcode());
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        product.setUnit(productDetails.getUnit());
        product.setPurchasePrice(productDetails.getPurchasePrice());
        product.setSellingPrice(productDetails.getSellingPrice());
        product.setMinimumStock(productDetails.getMinimumStock());
        product.setMaximumStock(productDetails.getMaximumStock());
        product.setReorderLevel(productDetails.getReorderLevel());
        product.setImageUrl(productDetails.getImageUrl());
        product.setBarcode(productDetails.getBarcode());
        product.setTaxable(productDetails.getTaxable());
        product.setTaxRate(productDetails.getTaxRate());
        
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    public boolean existsByProductCode(String productCode) {
        return productRepository.existsByProductCode(productCode);
    }

    public boolean existsByBarcode(String barcode) {
        return productRepository.existsByBarcode(barcode);
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.findBySearchTerm(searchTerm);
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryIdOrderByName(categoryId);
    }

    public List<Product> findAllActiveOrderByName() {
        return productRepository.findAllActiveOrderByName();
    }

    public String generateProductCode() {
        long count = productRepository.count();
        return "PROD" + String.format("%06d", count + 1);
    }
}