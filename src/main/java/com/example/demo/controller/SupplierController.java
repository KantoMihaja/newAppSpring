package com.example.demo.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.dto.SupplierDto;
import com.example.demo.dto.SupplierListResponse;
import com.example.demo.service.SupplierService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSuppliers(
            HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            Model model) {
        
        try {
            SupplierListResponse response;
            if (search == null || search.trim().isEmpty()) {
                response = supplierService.getAllSuppliers(session, page * size, size);
                model.addAttribute("totalPages", (int) Math.ceil((double) response.getTotal() / size));
            } else {
                response = supplierService.getSupplierByName(session, search);
                model.addAttribute("totalPages", 1);
            }

            model.addAttribute("suppliers", response.getData());
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("total", response.getTotal());
            model.addAttribute("search", search);

            return "suppliers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{name}")
    public String supplierDetails(@PathVariable String name, Model model) {
        // Implémentez cette méthode si besoin
        model.addAttribute("supplierName", name);
        return "supplier-details";
    }
}