package com.example.api_act_orders.domain.exception;

public class ProductClientException extends RuntimeException {
    public enum Reason {
        NOT_FOUND,
        UNAUTHORIZED,
        TIMEOUT,
        UNKNOWN
    }

    private final Reason reason;
    public ProductClientException(String message, Reason reason) {
        super(message);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
