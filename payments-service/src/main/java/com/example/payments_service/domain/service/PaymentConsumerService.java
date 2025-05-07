package com.example.payments_service.domain.service;

import com.example.payments_service.domain.ports.inputs.service.IPaymentService;
import com.example.payments_service.domain.record.PaymentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumerService {


    private IPaymentService paymentService;

    @KafkaListener(topics = "order-payment-created", groupId = "payment-group")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentCreatedEvent event = objectMapper.readValue(message, PaymentCreatedEvent.class);

        paymentService.create(event.orderId());
        System.out.println("Pagamento processado para o pedido: " + event.orderId());
    }
}
