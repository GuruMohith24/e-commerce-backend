# 🚀 Rakuten Interview Cheat Sheet: E-Commerce Backend

## 🛠️ Tech Stack & Key Concepts
- **Framework**: Spring Boot 3.4.0 (Latest stable)
- **Language**: Java 21 (Modern LTS)
- **Database**: PostgreSQL (Enterprise standard Relational DB)
- **ORM**: Spring Data JPA / Hibernate (Simplified DB interactions)
- **Security**: Spring Security + BCrypt (Password Encryption)
- **Documentation**: OpenAPI / Swagger UI (Auto-generated API docs)
- **Architecture**: Controller-Service-Repository (3-Layer Architecture)

## 💡 "Why did you do it this way?" (Common Questions)

### 1. Why Layered Architecture?
**"I separated concerns to make the code maintainable and testable."**
- **Controller**: Only handles HTTP requests and validation.
- **Service**: Contains the business logic (calculations, rules).
- **Repository**: Only talks to the database.

### 2. How do you handle invalid data?
**"I used Spring Validation (`@Valid`, `@NotNull`, `@Email`) to ensure data integrity."**
- If a user sends a bad email or negative price, the server automatically rejects it with a `400 Bad Request` before it even reaches the logic layer.

### 3. How do you handle large lists of products?
**"I implemented Pagination using `Pageable`."**
- Instead of loading 1 million products and crashing the memory, the client requests `?page=0&size=10`. This is crucial for scalability.

### 4. How did you secure the passwords?
**"I used `BCryptPasswordEncoder`."**
- We never store plain text passwords. We hash them one-way so even if the DB is stolen, user passwords remain safe.

### 5. What was the hardest challenge?
**"Resolving the Circular Dependency in API Documentation."**
- **Situation**: Orders contain Items, and Items link back to Orders.
- **Problem**: This caused an infinite loop when generating JSON for Swagger.
- **Solution**: I used `@JsonIgnore` on the child side (OrderItem) to break the chain.

## 🔗 Key Endpoints (For Demo)
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Create User**: `POST /api/users`
- **Get Products (Paged)**: `GET /api/products?page=0&size=5`
- **Create Order**: `POST /api/orders`

Good luck! You have built a solid foundation.
