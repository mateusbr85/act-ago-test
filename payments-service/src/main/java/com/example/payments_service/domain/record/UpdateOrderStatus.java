package com.example.payments_service.domain.record;

import com.example.payments_service.domain.enums.PaymentStatusEnum;

import java.util.UUID;

public record UpdateOrderStatus(
        UUID orderId,
        PaymentStatusEnum status
) {
}
