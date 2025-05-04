package com.example.api_act_orders.domain.record;

import com.example.api_act_orders.domain.enums.StatusEnum;

public record CreateOrderStatus(
        StatusEnum statusEnum
) {
}
