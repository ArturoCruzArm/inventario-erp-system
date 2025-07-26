package com.erp.system.controller;

import com.erp.system.entity.Customer;
import com.erp.system.entity.CustomerType;
import com.erp.system.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("customers", customerService.searchCustomers(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("customers", customerService.findAllActiveOrderByCompanyName());
        }
        return "customers/list";
    }

    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        Customer customer = new Customer();
        customer.setCustomerCode(customerService.generateCustomerCode());
        model.addAttribute("customer", customer);
        model.addAttribute("customerTypes", CustomerType.values());
        return "customers/form";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customers/view";
        }
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            model.addAttribute("customerTypes", CustomerType.values());
            return "customers/form";
        }
        return "redirect:/customers";
    }

    @PostMapping
    public String saveCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, 
                             Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customerTypes", CustomerType.values());
            return "customers/form";
        }

        try {
            if (customer.getId() == null) {
                customerService.createCustomer(customer);
                redirectAttributes.addFlashAttribute("success", "Customer created successfully!");
            } else {
                customerService.updateCustomer(customer.getId(), customer);
                redirectAttributes.addFlashAttribute("success", "Customer updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving customer: " + e.getMessage());
        }

        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("success", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public Object searchCustomersApi(@RequestParam String term) {
        return customerService.searchCustomers(term);
    }
}