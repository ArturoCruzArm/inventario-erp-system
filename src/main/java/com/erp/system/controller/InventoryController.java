package com.erp.system.controller;

import com.erp.system.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String listInventory(Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryLevels());
        return "inventory/list";
    }
}
