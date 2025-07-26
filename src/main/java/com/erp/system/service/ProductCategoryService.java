package com.erp.system.service;

import com.erp.system.entity.ProductCategory;
import com.erp.system.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    public List<ProductCategory> findAll() {
        return categoryRepository.findByActiveTrue();
    }

    public Optional<ProductCategory> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public ProductCategory save(ProductCategory category) {
        return categoryRepository.save(category);
    }

    public ProductCategory createCategory(ProductCategory category) {
        return categoryRepository.save(category);
    }

    public ProductCategory updateCategory(Long id, ProductCategory categoryDetails) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setParent(categoryDetails.getParent());
        
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        category.setActive(false);
        categoryRepository.save(category);
    }

    public List<ProductCategory> searchCategories(String searchTerm) {
        return categoryRepository.findBySearchTerm(searchTerm);
    }

    public List<ProductCategory> findRootCategories() {
        return categoryRepository.findRootCategoriesOrderByName();
    }

    public List<ProductCategory> findByParent(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public List<ProductCategory> findAllActiveOrderByName() {
        return categoryRepository.findByActiveTrue();
    }
}