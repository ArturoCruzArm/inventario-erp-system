package com.erp.system.controller;

import com.erp.system.entity.Invoice;
import com.erp.system.entity.Payment;
import com.erp.system.entity.Customer;
import com.erp.system.service.CustomerService;
import com.erp.system.service.SalesOrderService;
import com.erp.system.repository.InvoiceRepository;
import com.erp.system.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private SalesOrderService salesOrderService;

    @GetMapping
    public String financeDashboard(Model model) {
        // Financial metrics for dashboard
        List<Invoice> recentInvoices = invoiceRepository.findTop10ByOrderByCreatedAtDesc();
        List<Payment> recentPayments = paymentRepository.findTop10ByOrderByPaymentDateDesc();
        BigDecimal totalRevenue = invoiceRepository.getTotalRevenue();
        BigDecimal pendingAmount = invoiceRepository.getPendingAmount();
        
        model.addAttribute("recentInvoices", recentInvoices);
        model.addAttribute("recentPayments", recentPayments);
        model.addAttribute("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        model.addAttribute("pendingAmount", pendingAmount != null ? pendingAmount : BigDecimal.ZERO);
        
        return "finance/dashboard";
    }
    
    @GetMapping("/invoices")
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceRepository.findAll());
        return "finance/invoices/list";
    }
    
    @GetMapping("/invoices/new")
    public String newInvoiceForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("salesOrders", salesOrderService.findAll());
        return "finance/invoices/form";
    }
    
    @PostMapping("/invoices")
    public String saveInvoice(@ModelAttribute Invoice invoice, RedirectAttributes redirectAttributes) {
        try {
            if (invoice.getInvoiceDate() == null) {
                invoice.setInvoiceDate(LocalDate.now());
            }
            if (invoice.getDueDate() == null) {
                invoice.setDueDate(LocalDate.now().plusDays(30));
            }
            invoiceRepository.save(invoice);
            redirectAttributes.addFlashAttribute("success", "Invoice created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating invoice: " + e.getMessage());
        }
        return "redirect:/finance/invoices";
    }
    
    @GetMapping("/invoices/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            model.addAttribute("invoice", invoice.get());
            return "finance/invoices/view";
        }
        return "redirect:/finance/invoices";
    }
    
    @GetMapping("/payments")
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentRepository.findAll());
        return "finance/payments/list";
    }
    
    @GetMapping("/payments/new")
    public String newPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("invoices", invoiceRepository.findAll());
        return "finance/payments/form";
    }
    
    @PostMapping("/payments")
    public String savePayment(@ModelAttribute Payment payment, RedirectAttributes redirectAttributes) {
        try {
            if (payment.getPaymentDate() == null) {
                payment.setPaymentDate(LocalDate.now());
            }
            paymentRepository.save(payment);
            redirectAttributes.addFlashAttribute("success", "Payment recorded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error recording payment: " + e.getMessage());
        }
        return "redirect:/finance/payments";
    }
    
    @GetMapping("/reports")
    public String financialReports(Model model,
                                 @RequestParam(required = false) String period,
                                 @RequestParam(required = false) String type) {
        // Generate financial reports based on parameters
        if (period == null) period = "monthly";
        if (type == null) type = "revenue";
        
        model.addAttribute("period", period);
        model.addAttribute("type", type);
        
        // Add report data based on parameters
        switch (type) {
            case "revenue":
                model.addAttribute("reportData", getRevenueReport(period));
                break;
            case "payments":
                model.addAttribute("reportData", getPaymentReport(period));
                break;
            case "outstanding":
                model.addAttribute("reportData", getOutstandingReport());
                break;
        }
        
        return "finance/reports";
    }
    
    private Object getRevenueReport(String period) {
        // Implementation for revenue reporting
        return invoiceRepository.findAll();
    }
    
    private Object getPaymentReport(String period) {
        // Implementation for payment reporting
        return paymentRepository.findAll();
    }
    
    private Object getOutstandingReport() {
        // Implementation for outstanding amounts reporting
        return invoiceRepository.findByStatus(com.erp.system.entity.InvoiceStatus.PENDING);
    }
}
