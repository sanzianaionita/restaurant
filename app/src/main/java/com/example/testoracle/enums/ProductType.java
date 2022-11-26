package com.example.testoracle.enums;

public enum ProductType {
    PIZZA("PIZZA"),
    PASTE("PASTE"),
    CARNE("CARNE"),
    SALATE("SALATE"),
    BAUTURI("BAUTURI");

    private String code;

    ProductType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
