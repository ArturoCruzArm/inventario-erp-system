package com.erp.system.controller;

import com.erp.system.repository.*;
import com.erp.system.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());
        
        // Add comprehensive dashboard metrics
        addDashboardMetrics(model);
        
        return "dashboard/index";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "dashboard/admin";
    }

    @GetMapping("/manager/dashboard")
    public String managerDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "dashboard/manager";
    }

    @GetMapping("/sales/dashboard")
    public String salesDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "dashboard/sales";
    }

    @GetMapping("/purchase/dashboard")
    public String purchaseDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "dashboard/purchase";
    }

    @GetMapping("/inventory/dashboard")
    public String inventoryDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "dashboard/inventory";
    }

    @GetMapping("/finance/dashboard")
    public String financeDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "dashboard/finance";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
    
    private void addDashboardMetrics(Model model) {
        try {
            // Business Metrics
            Long totalCustomers = customerRepository.count();
            Long totalSuppliers = supplierRepository.count();
            Long totalProducts = productRepository.count();
            
            // Financial Metrics
            BigDecimal totalRevenue = invoiceRepository.getTotalRevenue();
            BigDecimal pendingAmount = invoiceRepository.getPendingAmount();
            Long totalInvoices = invoiceRepository.count();
            Long totalPayments = paymentRepository.count();
            
            // Sales & Purchase Metrics
            Long totalSalesOrders = salesOrderRepository.count();
            Long totalPurchaseOrders = purchaseOrderRepository.count();
            
            // Inventory Metrics
            var inventoryLevels = inventoryService.getInventoryLevels();
            Long lowStockItems = inventoryLevels.stream()
                .filter(item -> {
                    Object stock = ((Map<String, Object>) item).get("stock");
                    return stock != null && ((Number) stock).intValue() < 10;
                })
                .count();
            
            // Add to model
            model.addAttribute("totalCustomers", totalCustomers);
            model.addAttribute("totalSuppliers", totalSuppliers);
            model.addAttribute("totalProducts", totalProducts);
            model.addAttribute("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
            model.addAttribute("pendingAmount", pendingAmount != null ? pendingAmount : BigDecimal.ZERO);
            model.addAttribute("totalInvoices", totalInvoices);
            model.addAttribute("totalPayments", totalPayments);
            model.addAttribute("totalSalesOrders", totalSalesOrders);
            model.addAttribute("totalPurchaseOrders", totalPurchaseOrders);
            model.addAttribute("lowStockItems", lowStockItems);
            model.addAttribute("inventoryItems", inventoryLevels.size());
            
            // Recent activity
            model.addAttribute("recentInvoices", invoiceRepository.findTop10ByOrderByCreatedAtDesc());
            model.addAttribute("recentPayments", paymentRepository.findTop10ByOrderByPaymentDateDesc());
            
            // Dashboard cards data
            Map<String, Object> dashboardCards = new HashMap<>();
            dashboardCards.put("customers", Map.of("count", totalCustomers, "icon", "fas fa-users", "color", "primary"));
            dashboardCards.put("products", Map.of("count", totalProducts, "icon", "fas fa-boxes", "color", "success"));
            dashboardCards.put("revenue", Map.of("amount", totalRevenue, "icon", "fas fa-dollar-sign", "color", "info"));
            dashboardCards.put("orders", Map.of("count", totalSalesOrders + totalPurchaseOrders, "icon", "fas fa-shopping-cart", "color", "warning"));
            
            model.addAttribute("dashboardCards", dashboardCards);
            
        } catch (Exception e) {
            // Fallback metrics in case of errors
            model.addAttribute("totalCustomers", 0L);
            model.addAttribute("totalSuppliers", 0L);
            model.addAttribute("totalProducts", 0L);
            model.addAttribute("totalRevenue", BigDecimal.ZERO);
            model.addAttribute("pendingAmount", BigDecimal.ZERO);
            model.addAttribute("lowStockItems", 0L);
        }
    }
}