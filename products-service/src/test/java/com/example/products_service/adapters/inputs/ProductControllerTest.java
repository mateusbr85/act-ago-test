package com.example.products_service.adapters.inputs.controller;

import com.example.products_service.domain.entity.ProductEntity;
import com.example.products_service.domain.ports.inputs.service.IProductService;
import com.example.products_service.domain.record.CreateOrderRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductEntity sampleProduct;

    @BeforeEach
    void setup() {
        sampleProduct = new ProductEntity();
        sampleProduct.setId(UUID.randomUUID());
        sampleProduct.setName("Produto Teste");
        sampleProduct.setValue(BigDecimal.valueOf(100.0));
    }

    @Test
    void shouldListProducts() throws Exception {
        Page<ProductEntity> page = new PageImpl<>(Collections.singletonList(sampleProduct));
        when(productService.list(0, 10)).thenReturn(page);

        mockMvc.perform(get("/products?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        CreateOrderRecord record = new CreateOrderRecord("Produto Teste", BigDecimal.valueOf(100.0));
        when(productService.createOrder(any(CreateOrderRecord.class)))
                .thenReturn(sampleProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleProduct.getId().toString()));
    }

    @Test
    void shouldGetProductById() throws Exception {
        UUID id = sampleProduct.getId();
        when(productService.getById(id)).thenReturn(sampleProduct);

        mockMvc.perform(get("/products/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }
}
