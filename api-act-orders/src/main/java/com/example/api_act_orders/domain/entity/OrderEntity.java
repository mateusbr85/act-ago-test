package com.example.api_act_orders.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity  {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private UUID id;

    @Column(name = "order_name", nullable = false)
    private String name;

    @Column(name = "order_fk_product_id", nullable = false)
    private UUID productId;

    @Column(name = "order_value", nullable = false)
    private BigDecimal total;

    @Column(name = "order_created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
