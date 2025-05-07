package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import com.example.api_act_orders.domain.entity.StatusTypeEntity;
import com.example.api_act_orders.domain.enums.StatusEnum;
import com.example.api_act_orders.domain.record.CreateOrderStatus;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderStatusService;
import com.example.api_act_orders.domain.ports.outputs.repositories.IOrderStatusRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public void updateOrderStatus(UUID orderId, StatusEnum status) {
        OrderStatusEntity orderStatus = this.orderStatusRepository
                .findByOrder_Id(orderId)
                .orElseGet(() -> {
                    OrderStatusEntity newStatus = new OrderStatusEntity();
                    OrderEntity order = new OrderEntity();
                    order.setId(orderId);
                    newStatus.setOrder(order);
                    return newStatus;
                });
        OrderEntity order = new OrderEntity();
        StatusTypeEntity statusType = new StatusTypeEntity();


        statusType.setId((long) status.getCode());

        order.setId(orderId);

        orderStatus.setOrder(order);
        orderStatus.setStatusTypeEntity(statusType);

        this.orderStatusRepository.save(orderStatus);

    }
}
