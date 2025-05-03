package com.example.api_act_orders.domain.ports.inputs.service.request;

import java.util.UUID;

public record CreateOrderRequest(
    UUID productId,
    String name
) {
}
