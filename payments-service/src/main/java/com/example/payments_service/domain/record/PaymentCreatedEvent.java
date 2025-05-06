package com.example.payments_service.domain.record;

import java.util.UUID;

public record PaymentCreatedEvent(
        UUID orderId
) {
}
