package com.example.payments_service.domain.ports.inputs.service;

import com.example.payments_service.domain.entity.PaymentEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPaymentService {
    PaymentEntity createPayment(UUID orderId);
    Page<PaymentEntity> getAllPaymentWithPagination(int page, int size);
    PaymentEntity getById(UUID id);
    PaymentEntity getByOrderId(UUID id);
    PaymentEntity ProcessPayment(UUID id, UUID orderId, BigDecimal value);
}
