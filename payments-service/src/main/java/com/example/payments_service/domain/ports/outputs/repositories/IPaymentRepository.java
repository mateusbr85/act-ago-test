package com.example.payments_service.domain.ports.outputs.repositories;

import com.example.payments_service.domain.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    Optional<PaymentEntity> findByOrderId(UUID orderId);
}
