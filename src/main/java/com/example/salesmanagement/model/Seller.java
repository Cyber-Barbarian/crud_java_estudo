package com.example.salesmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private Double commissionRate;
    
    // Constructor with all fields
    public Seller(Long id, String name, String email, String phone, Double commissionRate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.commissionRate = commissionRate;
    }
    
    // Default constructor for JPA
    public Seller() {
    }
    
    // Constructor with required fields
    public Seller(String name, String email, String phone, Double commissionRate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.commissionRate = commissionRate;
    }
} 