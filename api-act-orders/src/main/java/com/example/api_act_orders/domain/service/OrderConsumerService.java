package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.domain.ports.inputs.service.IOrderStatusService;
import com.example.api_act_orders.domain.record.UpdateOrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumerService {

    private final IOrderStatusService orderStatusService;

    @KafkaListener(topics = "update-order-status", groupId = "order-group")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateOrderStatus event = objectMapper.readValue(message, UpdateOrderStatus.class);

        orderStatusService.updateOrderStatus(event.orderId(),event.status());
        System.out.println("Atualização do pedido:  " + event.orderId());
    }
}
