package com.example.products_service.domain.record;

import java.math.BigDecimal;

public record CreateOrderRecord(
        String name,
        BigDecimal value
) {
}
