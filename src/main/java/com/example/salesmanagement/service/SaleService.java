package com.example.salesmanagement.service;

import com.example.salesmanagement.model.Sale;
import com.example.salesmanagement.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    
    private final SaleRepository saleRepository;
    
    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }
    
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }
    
    @Transactional
    public Sale save(Sale sale) {
        // If this is a new sale (id is null), calculate the total amount and commission
        if (sale.getId() == null) {
            sale.setSaleDate(java.time.LocalDateTime.now());
            sale.setTotalAmount(sale.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(sale.getQuantity())));
            sale.setCommissionAmount(sale.getTotalAmount().multiply(java.math.BigDecimal.valueOf(sale.getSeller().getCommissionRate() / 100)));
        }
        return saleRepository.save(sale);
    }
    
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }
    
    public Sale findByIdOrThrow(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with id: " + id));
    }
} 