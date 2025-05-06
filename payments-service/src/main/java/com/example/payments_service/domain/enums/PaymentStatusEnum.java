package com.example.payments_service.domain.enums;

public enum PaymentStatusEnum {
    PENDENTE(1),
    PAGO(2),
    CANCELADO(3);

    private final int code;

    PaymentStatusEnum(int code) {this.code = code;}

    public int getCode(){return code;}

    public static PaymentStatusEnum fromCode(int code) {
        for(PaymentStatusEnum status: PaymentStatusEnum.values()){
            if(status.getCode() == code){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
