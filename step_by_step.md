# Guia Passo a Passo: Criando uma Aplicação de Gerenciamento de Vendas

## Pré-requisitos
1. Instalar o Java JDK 21 ou superior
2. Instalar o Maven 3.6 ou superior
3. Instalar um IDE (recomendado: IntelliJ IDEA ou VS Code)
4. Instalar o Git (opcional, para controle de versão)

## 1. Configuração Inicial do Projeto

### 1.1 Criar um novo projeto Spring Boot
1. Acesse [Spring Initializr](https://start.spring.io/)
2. Configure o projeto com as seguintes opções:
   - Project: Maven
   - Language: Java
   - Spring Boot: 3.2.3
   - Group: com.example
   - Artifact: sales-management
   - Packaging: Jar
   - Java: 21
3. Adicione as seguintes dependências:
   - Spring Web
   - Spring Data JPA
   - H2 Database
   - Lombok
   - Thymeleaf
4. Clique em "Generate" para baixar o projeto

### 1.2 Estrutura de Diretórios
Crie a seguinte estrutura de pastas dentro de `src/main/java/com/example/salesmanagement`:
```
├── config
├── controller
│   └── api
├── model
├── repository
├── service
└── exception
```

## 2. Implementação das Classes de Modelo

### 2.1 Criar a classe Seller
1. Crie o arquivo `Seller.java` em `src/main/java/com/example/salesmanagement/model`
2. Implemente a classe com o seguinte código:

```java
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
```

### 2.2 Criar a classe Product
1. Crie o arquivo `Product.java` em `src/main/java/com/example/salesmanagement/model`
2. Implemente a classe com o seguinte código:

```java
package com.example.salesmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer stockQuantity;
    
    // Constructor with all fields
    public Product(Long id, String name, String description, BigDecimal price, Integer stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Default constructor for JPA
    public Product() {
    }
    
    // Constructor with required fields
    public Product(String name, String description, BigDecimal price, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
```

### 2.3 Criar a classe Sale
1. Crie o arquivo `Sale.java` em `src/main/java/com/example/salesmanagement/model`
2. Implemente a classe com o seguinte código:

```java
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
```

## 3. Implementação dos Repositories

### 3.1 Criar SellerRepository
1. Crie o arquivo `SellerRepository.java` em `src/main/java/com/example/salesmanagement/repository`
2. Implemente a interface com o seguinte código:

```java
package com.example.salesmanagement.repository;

import com.example.salesmanagement.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
```

### 3.2 Criar ProductRepository
1. Crie o arquivo `ProductRepository.java` em `src/main/java/com/example/salesmanagement/repository`
2. Implemente a interface com o seguinte código:

```java
package com.example.salesmanagement.repository;

import com.example.salesmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

### 3.3 Criar SaleRepository
1. Crie o arquivo `SaleRepository.java` em `src/main/java/com/example/salesmanagement/repository`
2. Implemente a interface com o seguinte código:

```java
package com.example.salesmanagement.repository;

import com.example.salesmanagement.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
```

## 4. Implementação dos Services

### 4.1 Criar SellerService
1. Crie o arquivo `SellerService.java` em `src/main/java/com/example/salesmanagement/service`
2. Implemente a classe com o seguinte código:

```java
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
```

### 4.2 Criar ProductService
1. Crie o arquivo `ProductService.java` em `src/main/java/com/example/salesmanagement/service`
2. Implemente a classe com o seguinte código:

```java
package com.example.salesmanagement.service;

import com.example.salesmanagement.model.Product;
import com.example.salesmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    
    public Product findByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }
}
```

### 4.3 Criar SaleService
1. Crie o arquivo `SaleService.java` em `src/main/java/com/example/salesmanagement/service`
2. Implemente a classe com o seguinte código:

```java
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
```

## 5. Implementação dos Controllers

### 5.1 Criar SellerController
1. Crie o arquivo `SellerController.java` em `src/main/java/com/example/salesmanagement/controller`
2. Implemente a classe com o seguinte código:

```java
package com.example.salesmanagement.controller;

import com.example.salesmanagement.model.Seller;
import com.example.salesmanagement.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sellers")
public class SellerController {
    
    private final SellerService sellerService;
    
    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    
    @GetMapping
    public String listSellers(Model model) {
        model.addAttribute("sellers", sellerService.findAll());
        return "sellers/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellers/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("seller", sellerService.findByIdOrThrow(id));
        return "sellers/form";
    }
    
    @PostMapping("/save")
    public String saveSeller(@ModelAttribute Seller seller) {
        sellerService.save(seller);
        return "redirect:/sellers";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.deleteById(id);
        return "redirect:/sellers";
    }
}
```

### 5.2 Criar ProductController
1. Crie o arquivo `ProductController.java` em `src/main/java/com/example/salesmanagement/controller`
2. Implemente a classe com o seguinte código:

```java
package com.example.salesmanagement.controller;

import com.example.salesmanagement.model.Product;
import com.example.salesmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findByIdOrThrow(id));
        return "products/form";
    }
    
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
```

### 5.3 Criar SaleController
1. Crie o arquivo `SaleController.java` em `src/main/java/com/example/salesmanagement/controller`
2. Implemente a classe com o seguinte código:

```java
package com.example.salesmanagement.controller;

import com.example.salesmanagement.model.Sale;
import com.example.salesmanagement.service.SaleService;
import com.example.salesmanagement.service.SellerService;
import com.example.salesmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales")
public class SaleController {
    
    private final SaleService saleService;
    private final SellerService sellerService;
    private final ProductService productService;
    
    @Autowired
    public SaleController(SaleService saleService, SellerService sellerService, ProductService productService) {
        this.saleService = saleService;
        this.sellerService = sellerService;
        this.productService = productService;
    }
    
    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.findAll());
        return "sales/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("products", productService.findAll());
        return "sales/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("sale", saleService.findByIdOrThrow(id));
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("products", productService.findAll());
        return "sales/form";
    }
    
    @PostMapping("/save")
    public String saveSale(@ModelAttribute Sale sale) {
        saleService.save(sale);
        return "redirect:/sales";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteById(id);
        return "redirect:/sales";
    }
}
```

### 5.4 Criar HomeController
1. Crie o arquivo `HomeController.java` em `src/main/java/com/example/salesmanagement/controller`
2. Implemente a classe com o seguinte código:

```java
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
```

### 5.5 Criar API Controllers
1. Crie o arquivo `SellerApiController.java` em `src/main/java/com/example/salesmanagement/controller/api`
2. Implemente a classe com o seguinte código:

```java
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
```

## 6. Configuração do Banco de Dados

### 6.1 Criar schema.sql
1. Crie o arquivo `schema.sql` em `src/main/resources`
2. Implemente o script com o seguinte código:

```sql
-- Drop tables if they exist
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS sellers;

-- Create sellers table
CREATE TABLE sellers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    commission_rate DOUBLE NOT NULL
);

-- Create products table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL
);

-- Create sales table
CREATE TABLE sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    sale_date TIMESTAMP NOT NULL,
    commission_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES sellers(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### 6.2 Configurar application.properties
1. Edite o arquivo `src/main/resources/application.properties`
2. Adicione as configurações:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:salesdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Enable H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# SQL Initialization
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
spring.sql.init.continue-on-error=true

# Server Configuration
server.port=8080
```

## 7. Implementação de Inicialização de Dados

### 7.1 Criar DataInitializer
1. Crie o arquivo `DataInitializer.java` em `src/main/java/com/example/salesmanagement/config`
2. Implemente a classe com o seguinte código:

```java
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
```

### 7.2 Criar TestDataInitializer
1. Crie o arquivo `TestDataInitializer.java` em `src/test/java/com/example/salesmanagement/config`
2. Implemente a classe com o seguinte código:

```java
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
```

## 8. Configuração da Aplicação Principal

### 8.1 Configurar SalesManagementApplication
1. Edite o arquivo `SalesManagementApplication.java` em `src/main/java/com/example/salesmanagement`
2. Implemente a classe com o seguinte código:

```java
package com.example.salesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SalesManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesManagementApplication.class, args);
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
```

## 9. Executando a Aplicação

### 9.1 Compilar e Executar
1. Abra o terminal na pasta do projeto
2. Execute o comando: `mvn spring-boot:run`
3. Acesse a aplicação em: `http://localhost:8080`
4. Acesse o console H2 em: `http://localhost:8080/h2-console`
   - JDBC URL: jdbc:h2:mem:salesdb
   - Username: sa
   - Password: password

## 10. Próximos Passos (Melhorias Sugeridas)

1. Implementar autenticação e autorização
2. Adicionar validações mais robustas
3. Implementar logging
4. Adicionar cache
5. Implementar paginação nas listagens
6. Adicionar mais funcionalidades de relatórios
7. Implementar testes de integração
8. Configurar CI/CD
9. Adicionar monitoramento
10. Implementar tratamento de erros mais robusto 