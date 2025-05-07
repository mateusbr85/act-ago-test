package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import com.example.api_act_orders.domain.entity.StatusTypeEntity;
import com.example.api_act_orders.domain.record.CreateOrderStatus;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderStatusService;
import com.example.api_act_orders.domain.ports.outputs.repositories.IOrderStatusRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusService implements IOrderStatusService {

    private final IOrderStatusRepository orderStatusRepository;

    @Override
    public OrderStatusEntity create(CreateOrderStatus createOrderStatus, OrderEntity order) {
        OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
        StatusTypeEntity statusType = new StatusTypeEntity();

        statusType.setId((long) createOrderStatus.statusEnum().getCode());

        orderStatusEntity.setStatusTypeEntity(statusType);
        orderStatusEntity.setOrder(order);

        return this.orderStatusRepository.save(orderStatusEntity);
    }

    @Override
    public Page<OrderStatusEntity> list(int page, int size) {
        return null;
    }

    @Override
    public OrderStatusEntity getOrderById(Long id) {
        return null;
    }
}
