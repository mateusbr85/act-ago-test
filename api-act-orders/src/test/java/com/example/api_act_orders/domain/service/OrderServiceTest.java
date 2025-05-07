package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.adapters.outputs.client.productService.ProductClient;
import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.exception.ProductClientException;
import com.example.api_act_orders.domain.ports.inputs.request.CreateOrderRequest;
import com.example.api_act_orders.domain.ports.outputs.client.response.ProductResponse;
import com.example.api_act_orders.domain.ports.outputs.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private OrderService orderService;

    private UUID productId;
    private CreateOrderRequest createOrderRequest;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        createOrderRequest = new CreateOrderRequest(productId);
    }

    @Test
    void testCreateOrder_Success() {
        BigDecimal valueProduct = BigDecimal.valueOf(100.00);
        // Arrange
        ProductResponse productResponse = new ProductResponse(productId, "Product Name", valueProduct);
        when(productClient.getProductById(productId)).thenReturn(productResponse);

        OrderEntity savedOrder = new OrderEntity();
        savedOrder.setProductId(productId);
        savedOrder.setTotal(valueProduct);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrder);

        // Act
        OrderEntity createdOrder = orderService.createOrder(createOrderRequest);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(productId, createdOrder.getProductId());
        assertEquals(valueProduct, createdOrder.getTotal());
        verify(orderRepository, times(1)).save(any(OrderEntity.class)); // Verificar se o save foi chamado
    }

    @Test
    void testCreateOrder_ProductNotFound() {
        // Arrange
        when(productClient.getProductById(productId))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        ProductClientException exception = assertThrows(ProductClientException.class, () -> {
            orderService.createOrder(createOrderRequest);
        });
        assertEquals("Produto não encontrado: " + productId, exception.getMessage());
    }

    @Test
    void testCreateOrder_Unauthorized() {
        // Arrange
        when(productClient.getProductById(productId))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        // Act & Assert
        ProductClientException exception = assertThrows(ProductClientException.class, () -> {
            orderService.createOrder(createOrderRequest);
        });
        assertEquals("Acesso não autorizado ao serviço de produto", exception.getMessage());
    }

    @Test
    void testCreateOrder_UnknownError() {
        // Arrange
        when(productClient.getProductById(productId))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act & Assert
        ProductClientException exception = assertThrows(ProductClientException.class, () -> {
            orderService.createOrder(createOrderRequest);
        });
        assertEquals("Erro desconhecido no serviço de produto", exception.getMessage());
    }

    @Test
    void testListOrderByPage() {
        // Arrange
        OrderEntity order = new OrderEntity();
        order.setProductId(UUID.randomUUID());
        order.setTotal(BigDecimal.valueOf(150.00));

        List<OrderEntity> orders = List.of(order);
        Page<OrderEntity> page = new PageImpl<>(orders);

        when(orderRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        // Act
        Page<OrderEntity> result = orderService.listOrderByPage(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetOrderById_Success() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setProductId(productId);
        order.setTotal(BigDecimal.valueOf(200.00));

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));

        // Act
        OrderEntity result = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderById_NotFound() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(orderId);
        });

        assertEquals("Order not found with id: " + orderId, exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }

}
