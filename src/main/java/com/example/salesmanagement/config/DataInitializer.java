package com.example.salesmanagement.config;

import com.example.salesmanagement.model.Product;
import com.example.salesmanagement.model.Sale;
import com.example.salesmanagement.model.Seller;
import com.example.salesmanagement.repository.ProductRepository;
import com.example.salesmanagement.repository.SaleRepository;
import com.example.salesmanagement.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test") // Don't run in test profile
public class DataInitializer implements CommandLineRunner {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public DataInitializer(SellerRepository sellerRepository, 
                           ProductRepository productRepository, 
                           SaleRepository saleRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void run(String... args) {
        // Check if data already exists
        if (sellerRepository.count() > 0) {
            return; // Data already initialized
        }

        // Create sellers
        Seller seller1 = new Seller("João Silva", "joao.silva@example.com", "(11) 98765-4321", 5.0);
        Seller seller2 = new Seller("Maria Oliveira", "maria.oliveira@example.com", "(11) 91234-5678", 7.5);
        Seller seller3 = new Seller("Carlos Santos", "carlos.santos@example.com", "(11) 99876-5432", 6.0);
        
        List<Seller> sellers = sellerRepository.saveAll(Arrays.asList(seller1, seller2, seller3));
        
        // Create products
        Product product1 = new Product("Smartphone XYZ", "Latest smartphone with advanced features", 
                                      new BigDecimal("1299.99"), 50);
        Product product2 = new Product("Laptop ABC", "Powerful laptop for professionals", 
                                      new BigDecimal("3499.99"), 30);
        Product product3 = new Product("Tablet 123", "Versatile tablet for work and entertainment", 
                                      new BigDecimal("899.99"), 40);
        
        List<Product> products = productRepository.saveAll(Arrays.asList(product1, product2, product3));
        
        // Create sales
        Sale sale1 = new Sale(sellers.get(0), products.get(0), 2);
        Sale sale2 = new Sale(sellers.get(1), products.get(2), 1);
        Sale sale3 = new Sale(sellers.get(2), products.get(1), 1);
        
        saleRepository.saveAll(Arrays.asList(sale1, sale2, sale3));
        
        System.out.println("Database initialized with sample data");
    }
} 