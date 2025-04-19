package com.example.salesmanagement.controller;

import com.example.salesmanagement.model.Seller;
import com.example.salesmanagement.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sellers")
public class SellerController {
    
    private final SellerService sellerService;
    
    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    
    @GetMapping
    public String listSellers(Model model) {
        model.addAttribute("sellers", sellerService.findAll());
        return "sellers/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellers/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("seller", sellerService.findByIdOrThrow(id));
        return "sellers/form";
    }
    
    @PostMapping("/save")
    public String saveSeller(@ModelAttribute Seller seller) {
        sellerService.save(seller);
        return "redirect:/sellers";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.deleteById(id);
        return "redirect:/sellers";
    }
} 