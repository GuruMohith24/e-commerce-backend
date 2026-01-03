package com.example.e_commerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.exception.ResourceNotFoundException;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ProductRepository;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(new BigDecimal("1500.00"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(productId);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());
        assertEquals(new BigDecimal("1500.00"), response.getPrice());
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductNotFound() {
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Phone");
        request.setDescription("Smartphone");
        request.setPrice(new BigDecimal("999.99"));

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Phone");
        savedProduct.setDescription("Smartphone");
        savedProduct.setPrice(new BigDecimal("999.99"));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Phone", response.getName());
        assertEquals(new BigDecimal("999.99"), response.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct_WhenProductExists() {
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setPrice(new BigDecimal("100.00"));

        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("New Name");
        updateRequest.setDescription("Updated description");
        updateRequest.setPrice(new BigDecimal("200.00"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.updateProduct(productId, updateRequest);

        assertNotNull(response);
        assertEquals("New Name", response.getName());
        assertEquals(new BigDecimal("200.00"), response.getPrice());
    }

    @Test
    void deleteProduct_ShouldNotThrow_WhenProductExists() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenProductNotFound() {
        Long productId = 999L;
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });
    }
}
