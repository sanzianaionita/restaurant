package com.example.restaurant18.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private int id;
    private int userId;
    private String date;
    private String status; // ENUM
    private String address;
    private String description;

    public Order(int userId, String date, String status, String address) {
        this.userId = userId;
        this.date = date;
        this.status = status;
        this.address = address;
    }

    public Order(int userId, String date, String status, String address, String description) {
        this(userId, date, status, address);
        this.description = description;
    }

    public Order(int id, int userId, String date, String status, String address) {
        this(userId, date, status, address);
        this.id = id;
    }

    public Order(int id, int userId, String date, String status, String address, String description) {
        this(userId, date, status, address);
        this.description = description;
        this.id = id;
    }

    public Order(int id, int userId, Date date, String status, String address) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        this.date = simpleDateFormat.format(date);
        this.address = address;
    }

    public Order(int id, int userId, Date date, String status, String address, String description) {
        this(id, userId, date, status, address);
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Order){
            Order objorder = (Order) obj;

            if (objorder.getId() == this.getId() &&
                    objorder.getUserId() == this.getUserId() &&
                    objorder.getDate().equals(this.getDate()) &&
                    objorder.getStatus().equals(this.getStatus())
            )
                return true;
            return false;
        }
        else return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "userId: " + date + "\n" + "date: " + date + "\n" + "status: "+ status + "\n";
    }

    public void displayProducts(){

    }
}
