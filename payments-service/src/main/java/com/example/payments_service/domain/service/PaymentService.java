package com.example.payments_service.domain.service;

import com.example.payments_service.adapters.outputs.kafka.OrderStatusEventPublisher;
import com.example.payments_service.domain.entity.PaymentEntity;
import com.example.payments_service.domain.enums.PaymentStatusEnum;
import com.example.payments_service.domain.ports.inputs.service.IPaymentService;
import com.example.payments_service.domain.ports.outputs.repositories.IPaymentRepository;
import com.example.payments_service.domain.record.UpdateOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {


    private final IPaymentRepository paymentRepository;

    private final OrderStatusEventPublisher orderStatusEventPublisher;


    @Override
    public PaymentEntity createPayment(UUID orderId) {
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(orderId);
        payment.setStatus(PaymentStatusEnum.PENDENTE);
        return this.paymentRepository.save(payment);
    }

    @Override
    public Page<PaymentEntity> getAllPaymentWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.paymentRepository.findAll(pageable);
    }

    @Override
    public PaymentEntity getById(UUID id) {
        return this.paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public PaymentEntity getByOrderId(UUID id) {
        return this.paymentRepository.findByOrderId(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public PaymentEntity ProcessPayment(UUID id, UUID orderId, BigDecimal value) {
        PaymentEntity payment = new PaymentEntity();
        payment.setId(id);

        PaymentStatusEnum status=  this.simulatedPayment(value);

        payment.setStatus(status);

        UpdateOrderStatus event = new UpdateOrderStatus(orderId,status);
        this.orderStatusEventPublisher.publish(event);

        return paymentRepository.save(payment);

    }


    private PaymentStatusEnum simulatedPayment (BigDecimal value) {
        if(value.compareTo(BigDecimal.ZERO) <= 0) {
            return PaymentStatusEnum.INVALIDO;
        }

        return PaymentStatusEnum.PAGO;
    }
}
