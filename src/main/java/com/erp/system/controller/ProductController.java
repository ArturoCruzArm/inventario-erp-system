package com.erp.system.controller;

import com.erp.system.entity.Product;
import com.erp.system.entity.ProductCategory;
import com.erp.system.service.InventoryService;
import com.erp.system.service.ProductCategoryService;
import com.erp.system.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String listProducts(Model model, 
                              @RequestParam(required = false) String search,
                              @RequestParam(required = false) Long category,
                              @RequestParam(required = false) String status) {
        List<Product> products;
        
        // Apply filters
        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchProducts(search);
        } else if (category != null) {
            products = productService.findByCategory(category);
        } else {
            products = productService.findAll();
        }
        
        // Filter by status
        if ("active".equals(status)) {
            products = products.stream().filter(p -> p.getActive() != null && p.getActive()).toList();
        } else if ("inactive".equals(status)) {
            products = products.stream().filter(p -> p.getActive() == null || !p.getActive()).toList();
        }
        
        // Add current stock information for each product
        Map<Long, Integer> currentStocks = new HashMap<>();
        for (Product product : products) {
            currentStocks.put(product.getId(), inventoryService.getCurrentStock(product.getId()));
        }
        
        model.addAttribute("products", products);
        model.addAttribute("currentStocks", currentStocks);
        model.addAttribute("categories", categoryService.findAll());
        
        // Preserve search parameters
        model.addAttribute("param", Map.of(
            "search", search != null ? search : "",
            "category", category != null ? category.toString() : "",
            "status", status != null ? status : ""
        ));
        
        return "products/list";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        Product product = new Product();
        product.setActive(true); // Default to active
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "products/form";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("currentStock", inventoryService.getCurrentStock(id));
            model.addAttribute("movements", inventoryService.getMovementsByProduct(id));
            return "products/view";
        }
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("categories", categoryService.findAll());
            return "products/form";
        }
        return "redirect:/products";
    }

    @PostMapping
    public String saveProduct(@Valid @ModelAttribute Product product, BindingResult result, 
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "products/form";
        }

        try {
            if (product.getId() == null) {
                productService.save(product);
                redirectAttributes.addFlashAttribute("success", "Product created successfully!");
            } else {
                productService.save(product);
                redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving product: " + e.getMessage());
        }

        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public Object searchProductsApi(@RequestParam String term) {
        return productService.searchProducts(term);
    }

    @GetMapping("/api/{id}/stock")
    @ResponseBody
    public Map<String, Object> getProductStock(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("stock", inventoryService.getCurrentStock(id));
        return response;
    }
}