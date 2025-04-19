package com.example.salesmanagement.config;

import com.example.salesmanagement.model.Product;
import com.example.salesmanagement.model.Sale;
import com.example.salesmanagement.model.Seller;
import com.example.salesmanagement.repository.ProductRepository;
import com.example.salesmanagement.repository.SaleRepository;
import com.example.salesmanagement.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@TestConfiguration
@Profile("test")
public class TestDataInitializer {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Bean
    public void initializeTestData() {
        // Create test sellers
        Seller testSeller = new Seller("Test Seller", "test.seller@example.com", "(11) 99999-9999", 5.0);
        sellerRepository.save(testSeller);

        // Create test products
        Product testProduct = new Product("Test Product", "Test Description", 
                                         new BigDecimal("99.99"), 10);
        productRepository.save(testProduct);

        // Create test sale
        Sale testSale = new Sale(testSeller, testProduct, 1);
        saleRepository.save(testSale);
    }
} 