package com.example.api_act_orders.domain.record;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID productId,
        BigDecimal total
) {
}
