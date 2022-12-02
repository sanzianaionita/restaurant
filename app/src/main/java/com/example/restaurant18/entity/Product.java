package com.example.restaurant18.entity;

import java.util.Objects;

public class Product {

    private int id;

    private String productType;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Integer productQuantity;

    public Product() {

    }

    public Product(String productName, String productDescription, Double productPrice, Integer productQuantity, String productType) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                productName.equals(product.productName) &&
                productDescription.equals(product.productDescription) &&
                productPrice.equals(product.productPrice) &&
                productQuantity.equals(product.productQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productDescription, productPrice, productQuantity);
    }
}
