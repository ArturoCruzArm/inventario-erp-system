package com.erp.system.controller;

import com.erp.system.entity.PurchaseOrder;
import com.erp.system.entity.OrderStatus;
import com.erp.system.service.PurchaseOrderService;
import com.erp.system.service.SupplierService;
import com.erp.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public String listPurchaseOrders(Model model) {
        model.addAttribute("purchaseOrders", purchaseOrderService.findAll());
        return "purchase/list";
    }
    
    @GetMapping("/new")
    public String newPurchaseOrderForm(Model model) {
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        return "purchase/form";
    }
    
    @PostMapping
    public String savePurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, RedirectAttributes redirectAttributes) {
        try {
            if (purchaseOrder.getOrderDate() == null) {
                purchaseOrder.setOrderDate(LocalDate.now());
            }
            if (purchaseOrder.getStatus() == null) {
                purchaseOrder.setStatus(OrderStatus.PENDING);
            }
            purchaseOrderService.save(purchaseOrder);
            redirectAttributes.addFlashAttribute("success", "Purchase order created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating purchase order: " + e.getMessage());
        }
        return "redirect:/purchase";
    }
    
    @GetMapping("/{id}")
    public String viewPurchaseOrder(@PathVariable Long id, Model model) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.findById(id);
        if (purchaseOrder.isPresent()) {
            model.addAttribute("purchaseOrder", purchaseOrder.get());
            return "purchase/view";
        }
        return "redirect:/purchase";
    }
    
    @GetMapping("/{id}/edit")
    public String editPurchaseOrderForm(@PathVariable Long id, Model model) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.findById(id);
        if (purchaseOrder.isPresent()) {
            model.addAttribute("purchaseOrder", purchaseOrder.get());
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("orderStatuses", OrderStatus.values());
            return "purchase/form";
        }
        return "redirect:/purchase";
    }
    
    @PostMapping("/{id}/status")
    public String updatePurchaseOrderStatus(@PathVariable Long id, 
                                          @RequestParam OrderStatus status, 
                                          RedirectAttributes redirectAttributes) {
        try {
            purchaseOrderService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Purchase order status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating status: " + e.getMessage());
        }
        return "redirect:/purchase/" + id;
    }
    
    @PostMapping("/{id}/receive")
    public String receivePurchaseOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            purchaseOrderService.receiveOrder(id);
            redirectAttributes.addFlashAttribute("success", "Purchase order received and inventory updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error receiving order: " + e.getMessage());
        }
        return "redirect:/purchase/" + id;
    }
    
    @PostMapping("/{id}/delete")
    public String deletePurchaseOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            purchaseOrderService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Purchase order deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting purchase order: " + e.getMessage());
        }
        return "redirect:/purchase";
    }
}
