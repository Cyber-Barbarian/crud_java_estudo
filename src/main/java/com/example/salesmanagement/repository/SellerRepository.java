package com.example.salesmanagement.repository;

import com.example.salesmanagement.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
} 