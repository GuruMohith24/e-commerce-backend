package com.example.e_commerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.e_commerce.dto.OrderRequest;
import com.example.e_commerce.dto.OrderResponse;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldCalculateTotalCorrectly() {
        // Arrange
        Long userId = 1L;
        Long productId = 100L;

        User user = new User(userId, "Buyer", "buyer@example.com", "pass");
        Product product = new Product(productId, "Laptop", "1000.00"); // Price = 1000

        OrderRequest request = new OrderRequest();
        request.setUserId(userId);

        OrderRequest.OrderItemRequest itemRequest = new OrderRequest.OrderItemRequest();
        itemRequest.setProductId(productId);
        itemRequest.setQuantity(2); // Buying 2 items

        request.setItems(Collections.singletonList(itemRequest));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Mock the save to return the order with the calculated total (simulating DB
        // behavior or capturing argument)
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(123L); // Simulate ID generation
            return order;
        });

        // Act
        OrderResponse response = orderService.createOrder(request);

        // Assert
        assertNotNull(response);
        // 1000.00 * 2 = 2000.00
        assertEquals(new BigDecimal("2000.00"), response.getTotalAmount());
    }
}
