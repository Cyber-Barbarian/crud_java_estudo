package com.example.salesmanagement.controller.api;

import com.example.salesmanagement.model.Seller;
import com.example.salesmanagement.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerApiController {
    
    private final SellerService sellerService;
    
    @Autowired
    public SellerApiController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        return sellerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.save(seller);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        if (!sellerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        seller.setId(id);
        return ResponseEntity.ok(sellerService.save(seller));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        if (!sellerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        sellerService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 