package com.example.api_act_orders.domain.record;

import java.util.UUID;

public record PaymentCreatedEvent(
        UUID orderId
) {
}
