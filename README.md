# 🛍️ Enterprise E-Commerce Backend

A robust, enterprise-grade RESTful API built with **Spring Boot 3.4** and **PostgreSQL**. Designed to handle real-world scenarios like pagination, input validation, and secure order processing.

---

## 🚀 Key Features

### 📦 Product Management
- **Scalable**: Supports pagination (`?page=0&size=10`) to handle large datasets.
- **Searchable**: Filter products by name or price range.
- **Secure**: Input validation ensures only valid product data is saved.

### 👤 User Management
- **Security-First**: Passwords are securely hashed using **BCrypt** before storage.
- **Validation**: Strict validation for emails and inputs (`@Email`, `@NotBlank`).

### 🛒 Order Processing
- **Atomic Transactions**: Orders ensure consistency between User, Products, and Items.
- **Smart Logic**: Automatically calculates order totals based on current product prices.
- **API**: Fetch orders by User ID or view full history.

### 🛠️ Developer Experience
- **Swagger UI / OpenAPI**: Interactive API documentation available at `http://localhost:8080/swagger-ui.html`.
- **Unit Testing**: Core logic covered by JUnit 5 and Mockito tests.
- **Global Error Handling**: Centralized exception handling transforms server errors into clean JSON responses.

---

## 🏗️ Technology Stack

| Category | Technology | Usage |
|----------|------------|-------|
| **Core** | Java 21 | Modern LTS Version |
| **Framework** | Spring Boot 3.4.0 | Application Framework |
| **Database** | PostgreSQL | Relational Database |
| **ORM** | Spring Data JPA (Hibernate) | Database Interactions |
| **Security** | Spring Security | BCrypt Password Encryption |
| **Testing** | JUnit 5, Mockito | Unit Testing |
| **Docs** | SpringDoc OpenAPI (Swagger) | API Visualization |

---

## ⚙️ Setup & Installation

### 1. Prerequisites
- Java 21 or higher
- PostgreSQL installed and running

### 2. Database Setup
Create a local PostgreSQL database:
```sql
CREATE DATABASE ecommerce_db;
```
*Note: Update `src/main/resources/application.properties` with your DB credentials if they differ from standard.*

### 3. Run the Application
```bash
# Clone the repo
git clone <repository-url>

# Build and Run
./mvnw spring-boot:run
```

### 4. Explore the API
Once running, visit:
👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## 📚 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/users` | Register a new user |
| **GET** | `/api/products` | Get all products (supports `?page=x&size=y`) |
| **POST** | `/api/orders` | Create a new order |
| **GET** | `/api/orders/user/{id}` | Get specific user's orders |

---

## 💡 Design Decisions (Interview Prep)

- **Why Layered Architecture?**
  - Separates concerns: Controller (HTTP), Service (Logic), Repository (Data). Makes code testable and maintainable.

- **Why DTOs (Data Transfer Objects)?**
  - Decouples the internal database entities from the external API contract. Allows safer data exposure.

- **Why Pagination?**
  - Performance and Scalability. Returning 1,000,000 records in one request would crash the server; paging makes it safe.

---
*Built within 1 week for Rakuten Technical Assessment.*
