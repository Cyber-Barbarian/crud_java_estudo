-- Insert mock sellers
INSERT INTO sellers (name, email, phone, commission_rate) VALUES
('João Silva', 'joao.silva@example.com', '(11) 98765-4321', 5.0),
('Maria Oliveira', 'maria.oliveira@example.com', '(11) 91234-5678', 7.5),
('Carlos Santos', 'carlos.santos@example.com', '(11) 99876-5432', 6.0),
('Ana Pereira', 'ana.pereira@example.com', '(11) 92345-6789', 8.0),
('Roberto Almeida', 'roberto.almeida@example.com', '(11) 98765-1234', 4.5);

-- Insert mock products
INSERT INTO products (name, description, price, stock_quantity) VALUES
('Smartphone XYZ', 'Latest smartphone with advanced features', 1299.99, 50),
('Laptop ABC', 'Powerful laptop for professionals', 3499.99, 30),
('Tablet 123', 'Versatile tablet for work and entertainment', 899.99, 40),
('Smartwatch Pro', 'Feature-rich smartwatch with health monitoring', 499.99, 60),
('Wireless Headphones', 'High-quality wireless headphones with noise cancellation', 299.99, 75);

-- Insert mock sales
INSERT INTO sales (seller_id, product_id, quantity, total_amount, sale_date, commission_amount) VALUES
(1, 1, 2, 2599.98, '2023-01-15 10:30:00', 129.99),
(2, 3, 1, 899.99, '2023-01-16 14:45:00', 67.50),
(3, 2, 1, 3499.99, '2023-01-17 09:15:00', 209.99),
(4, 5, 3, 899.97, '2023-01-18 16:20:00', 71.99),
(5, 4, 2, 999.98, '2023-01-19 11:30:00', 44.99),
(1, 3, 1, 899.99, '2023-01-20 13:45:00', 45.00),
(2, 1, 1, 1299.99, '2023-01-21 15:30:00', 97.50),
(3, 5, 2, 599.98, '2023-01-22 10:15:00', 36.00),
(4, 2, 1, 3499.99, '2023-01-23 14:20:00', 279.99),
(5, 4, 1, 499.99, '2023-01-24 09:30:00', 22.50); 