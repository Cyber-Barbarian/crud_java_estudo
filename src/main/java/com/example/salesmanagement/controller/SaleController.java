package com.example.salesmanagement.controller;

import com.example.salesmanagement.model.Sale;
import com.example.salesmanagement.service.SaleService;
import com.example.salesmanagement.service.SellerService;
import com.example.salesmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales")
public class SaleController {
    
    private final SaleService saleService;
    private final SellerService sellerService;
    private final ProductService productService;
    
    @Autowired
    public SaleController(SaleService saleService, SellerService sellerService, ProductService productService) {
        this.saleService = saleService;
        this.sellerService = sellerService;
        this.productService = productService;
    }
    
    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.findAll());
        return "sales/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("products", productService.findAll());
        return "sales/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("sale", saleService.findByIdOrThrow(id));
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("products", productService.findAll());
        return "sales/form";
    }
    
    @PostMapping("/save")
    public String saveSale(@ModelAttribute Sale sale) {
        saleService.save(sale);
        return "redirect:/sales";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteById(id);
        return "redirect:/sales";
    }
} 