package com.example.payments_service.domain.entity;

import com.example.payments_service.domain.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentEntity {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private UUID id;

    @Column(name = "payment_fk_order_id")
    private UUID orderId;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @Column(name = "payment_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
