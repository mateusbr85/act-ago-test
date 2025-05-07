package com.example.api_act_orders.adapters.outputs.kafka;

import com.example.api_act_orders.domain.record.PaymentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate;
    public PaymentEventPublisher(KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentCreatedEvent event) {
        kafkaTemplate.send("order-payment-created", event.orderId().toString(), event);
    }
}
