package com.example.salesmanagement.repository;

import com.example.salesmanagement.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
} 