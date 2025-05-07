package com.example.payments_service.domain.service;

import com.example.payments_service.domain.entity.PaymentEntity;
import com.example.payments_service.domain.enums.PaymentStatusEnum;
import com.example.payments_service.domain.ports.inputs.service.IPaymentService;
import com.example.payments_service.domain.ports.outputs.repositories.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {


    private final IPaymentRepository paymentRepository;


    @Override
    public PaymentEntity create(UUID orderId) {
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(orderId);
        payment.setStatus(PaymentStatusEnum.PENDENTE);
        return this.paymentRepository.save(payment);
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
