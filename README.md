# Sales Management System

A simple CRUD application built with Spring Boot, H2 Database, and Thymeleaf for managing sellers, products, and sales.

## Features

- Manage sellers (create, read, update, delete)
- Manage products (create, read, update, delete)
- Manage sales (create, read, update, delete)
- Automatic calculation of total amount and commission
- Responsive web interface using Bootstrap
- Java 21 features for improved code quality and performance

## Requirements

- Java 21 or higher
- Maven 3.6 or higher

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the following command:
   ```
   mvn spring-boot:run
   ```
4. Open your browser and access: http://localhost:8080

## H2 Database Console

The H2 database console is available at: http://localhost:8080/h2-console

Database credentials:
- JDBC URL: jdbc:h2:mem:salesdb
- Username: sa
- Password: password

## Project Structure

- `src/main/java/com/example/salesmanagement/`
  - `controller/` - REST controllers
  - `model/` - Entity classes
  - `repository/` - JPA repositories
  - `service/` - Business logic
- `src/main/resources/`
  - `templates/` - Thymeleaf templates
  - `application.properties` - Application configuration 