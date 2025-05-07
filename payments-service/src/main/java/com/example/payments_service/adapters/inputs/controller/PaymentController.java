package com.example.payments_service.adapters.inputs.controller;

import com.example.payments_service.domain.entity.PaymentEntity;
import com.example.payments_service.domain.ports.inputs.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentEntity> createPayment(
            @RequestParam UUID orderId
    ) {
        PaymentEntity createdPayment = paymentService.createPayment(orderId);
        return ResponseEntity.ok(createdPayment);
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentEntity> processPayment(
            @RequestParam UUID paymentId,
            @RequestParam UUID orderId,
            @RequestParam BigDecimal amount
    ) {
        PaymentEntity processedPayment = paymentService.ProcessPayment(paymentId, orderId, amount);
        return ResponseEntity.ok(processedPayment);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentEntity>> listPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PaymentEntity> payments = paymentService.getAllPaymentWithPagination(page, size);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentEntity> getPaymentById(
            @PathVariable UUID id
    ) {
        PaymentEntity payment = paymentService.getById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentEntity> getPaymentByOrderId(
            @PathVariable UUID orderId
    ) {
        PaymentEntity payment = paymentService.getByOrderId(orderId);
        return ResponseEntity.ok(payment);
    }
}
