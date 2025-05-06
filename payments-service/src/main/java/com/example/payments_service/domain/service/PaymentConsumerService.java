package com.example.payments_service.domain.service;

import com.example.payments_service.domain.ports.inputs.service.IPaymentService;
import com.example.payments_service.domain.record.PaymentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumerService {

    @Autowired
    private IPaymentService paymentService;

    @KafkaListener(topics = "order-payment-created", groupId = "payment-group")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentCreatedEvent event = objectMapper.readValue(message, PaymentCreatedEvent.class);

        paymentService.create(event.orderId());
        System.out.println("Pagamento processado para o pedido: " + event.orderId());
    }
}
