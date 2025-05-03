package com.example.api_act_orders.domain.ports.outputs.repositories;

import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {
}
