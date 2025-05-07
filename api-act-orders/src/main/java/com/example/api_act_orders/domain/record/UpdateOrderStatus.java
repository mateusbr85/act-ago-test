package com.example.api_act_orders.domain.record;

import com.example.api_act_orders.domain.enums.StatusEnum;

import java.util.UUID;

public record UpdateOrderStatus(
        UUID orderId,
        StatusEnum status
) {
}

