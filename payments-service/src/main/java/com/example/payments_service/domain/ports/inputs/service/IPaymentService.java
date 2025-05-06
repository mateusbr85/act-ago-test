package com.example.payments_service.domain.ports.inputs.service;

import com.example.payments_service.domain.entity.PaymentEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IPaymentService {
    PaymentEntity create(UUID orderId);
    Page<PaymentEntity> list(int page, int size);
    PaymentEntity getById(UUID id);
    PaymentEntity getByOrderId(UUID id);
}
