package com.example.restaurant18.entity;

public class OrderProduct {
    private int orderId;
    private int productId;
    private int productQuantity;

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
