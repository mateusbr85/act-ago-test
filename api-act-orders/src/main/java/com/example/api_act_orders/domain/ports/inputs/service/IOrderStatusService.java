package com.example.api_act_orders.domain.ports.inputs.service;

import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import com.example.api_act_orders.domain.enums.StatusEnum;
import com.example.api_act_orders.domain.record.CreateOrderStatus;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IOrderStatusService {
    OrderStatusEntity create(CreateOrderStatus createOrderStatus, OrderEntity order);
    Page<OrderStatusEntity> list(int page, int size);
    OrderStatusEntity getOrderById(Long id);
    void updateOrderStatus(UUID orderId, StatusEnum status);
}
