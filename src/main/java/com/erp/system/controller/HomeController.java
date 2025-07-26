package com.erp.system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());
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
}