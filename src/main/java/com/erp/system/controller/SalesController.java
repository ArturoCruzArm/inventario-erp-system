package com.erp.system.controller;

import com.erp.system.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesOrderService salesOrderService;

    @GetMapping
    public String listSalesOrders(Model model) {
        model.addAttribute("salesOrders", salesOrderService.findAll());
        return "sales/list";
    }
}
