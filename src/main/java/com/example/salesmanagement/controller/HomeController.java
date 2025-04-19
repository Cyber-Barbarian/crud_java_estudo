package com.example.salesmanagement.controller;

import com.example.salesmanagement.repository.ProductRepository;
import com.example.salesmanagement.repository.SaleRepository;
import com.example.salesmanagement.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public HomeController(SellerRepository sellerRepository, 
                         ProductRepository productRepository, 
                         SaleRepository saleRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Add counts to the model for the dashboard
        model.addAttribute("sellerCount", sellerRepository.count());
        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("saleCount", saleRepository.count());
        
        return "index";
    }
} 