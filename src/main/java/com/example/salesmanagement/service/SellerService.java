package com.example.salesmanagement.service;

import com.example.salesmanagement.model.Seller;
import com.example.salesmanagement.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    
    private final SellerRepository sellerRepository;
    
    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }
    
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }
    
    public Optional<Seller> findById(Long id) {
        return sellerRepository.findById(id);
    }
    
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }
    
    public void deleteById(Long id) {
        sellerRepository.deleteById(id);
    }
    
    public Seller findByIdOrThrow(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with id: " + id));
    }
} 