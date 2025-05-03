package com.example.api_act_orders.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_fk_order_id", referencedColumnName = "order_id", nullable = false, unique = true, columnDefinition = "uuid")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "order_status_fk_status_type_id", referencedColumnName = "status_type_id", nullable = false)
    private StatusTypeEntity statusTypeEntity;

    @Column(name = "order_status_created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

}
