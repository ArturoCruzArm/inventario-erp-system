package com.erp.system.controller;

import com.erp.system.entity.Supplier;
import com.erp.system.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        return "suppliers/list";
    }

    @GetMapping("/new")
    public String newSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/form";
    }

    @GetMapping("/{id}/edit")
    public String editSupplierForm(@PathVariable Long id, Model model) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if (supplier.isPresent()) {
            model.addAttribute("supplier", supplier.get());
            return "suppliers/form";
        }
        return "redirect:/suppliers";
    }

    @PostMapping
    public String saveSupplier(@Valid @ModelAttribute Supplier supplier, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "suppliers/form";
        }
        supplierService.save(supplier);
        redirectAttributes.addFlashAttribute("success", "Supplier saved successfully!");
        return "redirect:/suppliers";
    }

    @PostMapping("/{id}/delete")
    public String deleteSupplier(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        supplierService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Supplier deleted successfully!");
        return "redirect:/suppliers";
    }
}
