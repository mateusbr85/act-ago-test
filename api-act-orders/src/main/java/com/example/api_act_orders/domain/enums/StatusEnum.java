package com.example.api_act_orders.domain.enums;

public enum StatusEnum {
    PENDENTE(1),
    PAGO(2),
    CANCELADO(3);

    private final int id;

    StatusEnum(int id) {this.id = id;}

    public int getCode(){return id;}

    public static StatusEnum fromCode(int id) {
        for(StatusEnum status: StatusEnum.values()){
            if(status.getCode() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
