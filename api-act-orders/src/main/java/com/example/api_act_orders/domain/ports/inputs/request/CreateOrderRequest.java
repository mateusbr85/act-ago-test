package com.example.api_act_orders.domain.ports.inputs.request;

import java.util.UUID;

public record CreateOrderRequest(
    UUID productId
) {
}
