package com.erp.system.controller;

import com.erp.system.service.InventoryService;
import com.erp.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public String listInventory(Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryLevels());
        return "inventory/list";
    }
    
    @PostMapping("/adjust")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> adjustStock(@RequestBody Map<String, Object> request) {
        try {
            Long productId = Long.valueOf(request.get("productId").toString());
            String type = request.get("type").toString();
            Integer quantity = Integer.valueOf(request.get("quantity").toString());
            String reason = request.get("reason").toString();
            
            // Find the product
            Optional<com.erp.system.entity.Product> product = productService.findById(productId);
            if (product.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Product not found"));
            }
            
            // Calculate adjustment quantity based on type
            Integer adjustmentQuantity = switch (type) {
                case "add" -> quantity;
                case "remove" -> -quantity;
                case "set" -> {
                    Integer currentStock = inventoryService.getCurrentStock(productId);
                    yield quantity - currentStock;
                }
                default -> throw new IllegalArgumentException("Invalid adjustment type: " + type);
            };
            
            // Apply stock adjustment
            inventoryService.adjustStock(product.get(), adjustmentQuantity, reason);
            
            return ResponseEntity.ok(Map.of("success", true, "message", "Stock adjusted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
