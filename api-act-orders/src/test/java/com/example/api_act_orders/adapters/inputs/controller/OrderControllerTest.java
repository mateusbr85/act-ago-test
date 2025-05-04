package com.example.api_act_orders.adapters.inputs.controller;

import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderService;
import com.example.api_act_orders.domain.ports.inputs.request.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IOrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

    private OrderEntity sampleOrder;

    @BeforeEach
    void setup() {
        sampleOrder = new OrderEntity();
        sampleOrder.setId(UUID.randomUUID());
    }

    @Test
    void shouldListOrders() throws Exception {
        Page<OrderEntity> page = new PageImpl<>(Collections.singletonList(sampleOrder));
        when(orderService.listOrderByPage(0, 10)).thenReturn(page);

        mockMvc.perform(get("/orders?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void shouldCreateOrder() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(UUID.randomUUID());

        when(orderService.createOrder(any(CreateOrderRequest.class)))
                .thenReturn(sampleOrder);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleOrder.getId().toString()));
    }

    @Test
    void shouldGetOrderById() throws Exception {
        UUID id = sampleOrder.getId();
        when(orderService.getOrderById(id)).thenReturn(sampleOrder);

        mockMvc.perform(get("/orders/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }
}
