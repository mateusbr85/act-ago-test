package com.example.products_service.domain.service;

import com.example.products_service.domain.entity.ProductEntity;
import com.example.products_service.domain.ports.outputs.repositories.IProductRepository;
import com.example.products_service.domain.record.CreateOrderRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private UUID productId;
    private CreateOrderRecord createOrderRecord;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        createOrderRecord = new CreateOrderRecord("Produto Teste", BigDecimal.valueOf(150.0));
    }

    @Test
    void testCreateOrder_Success() {
        ProductEntity savedProduct = new ProductEntity();
        savedProduct.setId(productId);
        savedProduct.setName(createOrderRecord.name());
        savedProduct.setValue(createOrderRecord.value());

        when(productRepository.save(any(ProductEntity.class))).thenReturn(savedProduct);

        ProductEntity result = productService.createOrder(createOrderRecord);

        assertNotNull(result);
        assertEquals(createOrderRecord.name(), result.getName());
        assertEquals(createOrderRecord.value(), result.getValue());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testList_Success() {
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setName("Produto Paginado");
        product.setValue(BigDecimal.valueOf(99.0));

        Page<ProductEntity> page = new PageImpl<>(List.of(product));
        when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<ProductEntity> result = productService.list(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Produto Paginado", result.getContent().get(0).getName());
        verify(productRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetById_Success() {
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setName("Produto Encontrado");
        product.setValue(BigDecimal.valueOf(300.0));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductEntity result = productService.getById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Produto Encontrado", result.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetById_NotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getById(productId);
        });

        assertEquals("Order not found with id: " + productId, exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }
}
