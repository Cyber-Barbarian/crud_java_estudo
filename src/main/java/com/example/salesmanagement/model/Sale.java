package com.example.salesmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private BigDecimal totalAmount;
    
    @Column(nullable = false)
    private LocalDateTime saleDate;
    
    @Column(nullable = false)
    private BigDecimal commissionAmount;
    
    // Constructor with all fields
    public Sale(Long id, Seller seller, Product product, Integer quantity, 
                BigDecimal totalAmount, LocalDateTime saleDate, BigDecimal commissionAmount) {
        this.id = id;
        this.seller = seller;
        this.product = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
        this.commissionAmount = commissionAmount;
    }
    
    // Default constructor for JPA
    public Sale() {
    }
    
    // Constructor with required fields
    public Sale(Seller seller, Product product, Integer quantity) {
        this.seller = seller;
        this.product = product;
        this.quantity = quantity;
        this.saleDate = LocalDateTime.now();
        this.totalAmount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.commissionAmount = this.totalAmount.multiply(BigDecimal.valueOf(seller.getCommissionRate() / 100));
    }
} 