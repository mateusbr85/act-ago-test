package com.example.payments_service.adapters.outputs.kafka;

import com.example.payments_service.domain.record.UpdateOrderStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusEventPublisher {

    private final KafkaTemplate<String, UpdateOrderStatus> kafkaTemplate;

    public OrderStatusEventPublisher(KafkaTemplate<String, UpdateOrderStatus> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(UpdateOrderStatus event) {
        kafkaTemplate.send("update-order-status", event.orderId().toString(), event);
    }
}
