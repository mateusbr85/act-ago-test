package com.example.api_act_orders.domain.ports.outputs.client.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal value
) {
}
