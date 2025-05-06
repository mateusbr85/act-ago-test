package com.example.payments_service.domain.service;

import com.example.payments_service.domain.entity.PaymentEntity;
import com.example.payments_service.domain.ports.inputs.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentService paymentService;


    @Override
    public PaymentEntity create(UUID orderId) {
        return null;
    }

    @Override
    public Page<PaymentEntity> list(int page, int size) {
        return null;
    }

    @Override
    public PaymentEntity getById(UUID id) {
        return null;
    }

    @Override
    public PaymentEntity getByOrderId(UUID id) {
        return null;
    }
}
