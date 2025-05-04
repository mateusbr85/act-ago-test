package com.example.api_act_orders.domain.ports.inputs.service;

import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.ports.inputs.request.CreateOrderRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IOrderService {
    OrderEntity createOrder(CreateOrderRequest createOrderRequest);
    Page<OrderEntity> listOrderByPage(int page, int size);
    OrderEntity getOrderById(UUID id);
}
