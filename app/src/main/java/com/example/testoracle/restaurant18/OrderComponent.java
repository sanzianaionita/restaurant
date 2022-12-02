package com.example.restaurant18;

public class OrderComponent {

    private Product orderProduct;
    private int orderProductQuantity;

    public OrderComponent(Product orderProduct, int orderProductQuantity) {
        this.orderProduct = orderProduct;
        this.orderProductQuantity = orderProductQuantity;
    }

    public Product getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(Product orderProduct) {
        this.orderProduct = orderProduct;
    }

    public int getOrderProductQuantity() {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(int orderProductQuantity) {
        this.orderProductQuantity = orderProductQuantity;
    }

}
