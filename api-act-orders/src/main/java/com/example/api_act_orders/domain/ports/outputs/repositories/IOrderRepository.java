package com.example.api_act_orders.domain.ports.outputs.repositories;

import com.example.api_act_orders.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderRepository extends JpaRepository<OrderEntity, UUID> {
}
