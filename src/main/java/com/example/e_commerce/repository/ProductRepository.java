package com.example.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e_commerce.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // This is empty, but it inherits powerful methods automatically.
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}