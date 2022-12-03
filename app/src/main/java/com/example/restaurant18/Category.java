package com.example.restaurant18;

import java.util.ArrayList;
import com.example.restaurant18.entity.Product;

public class Category {

    private String categoryName;
    private ArrayList<Product> productArrayList;

    public Category(String categoryName, ArrayList<Product> productArrayList)
    {
        this.categoryName = categoryName;
        this.productArrayList = productArrayList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> produsArrayList) {
        this.productArrayList = produsArrayList;
    }




}
