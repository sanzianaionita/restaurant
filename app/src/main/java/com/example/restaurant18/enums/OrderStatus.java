package com.example.restaurant18.enums;

public enum OrderStatus {
    DONE("DONE"),
    DELIVERING("DELIVERING"),
    PREPARING("PREPARING"),
    PLACED("PLACED");

    private String code;

    OrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
