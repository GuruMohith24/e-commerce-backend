package com.example.e_commerce.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.exception.ResourceNotFoundException;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl());
    }

    public List<ProductResponse> searchByName(String keyword) {
        // Use ProductRepository.findByNameContainingIgnoreCase()
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> filterByPriceRange(Double minPrice, Double maxPrice) {
        // Use ProductRepository.findByPriceBetween()
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
