package com.example.api_act_orders.domain.ports.outputs.repositories;

import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IOrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {
    Optional<OrderStatusEntity> findByOrder_Id(UUID orderId);
}
