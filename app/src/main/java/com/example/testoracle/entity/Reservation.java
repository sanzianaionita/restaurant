package com.example.testoracle.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private int id;
    private int userId;
    private String  date;
    private int tableNumber;
    private int hour;
    private int nrOfPeople;
    private String details;


    public Reservation(int userId, String date, int tableNumber, int hour, int nrOfPeople) {
        this.userId = userId;
        this.date = date;
        this.tableNumber = tableNumber;
        this.hour = hour;
        this.nrOfPeople = nrOfPeople;
    }

    public Reservation(int userId, Date date, int tableNumber, int hour, int nrOfPeople) {
        this.userId = userId;
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        this.date = simpleDateFormat.format(date);
        this.tableNumber = tableNumber;
        this.hour = hour;
        this.nrOfPeople = nrOfPeople;
    }

    public Reservation(int userId, String date, int tableNumber, int hour, int nrOfPeople, String details){
        this(userId, date, tableNumber, hour, nrOfPeople);
        this.details = details;
    }

    public Reservation(int userId, Date date, int tableNumber, int hour, int nrOfPeople, String details){
        this(userId, date, tableNumber, hour, nrOfPeople);
        this.details = details;
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

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getNrOfPeople() {
        return nrOfPeople;
    }

    public void setNrOfPeople(int nrOfPeople) {
        this.nrOfPeople = nrOfPeople;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Reservation){
            Reservation objreservation = (Reservation) obj;

            if (objreservation.getId() == this.getId() &&
                    objreservation.getUserId() == this.getUserId() &&
                    objreservation.getDate().equals(this.getDate()) &&
                    objreservation.getTableNumber() == this.getTableNumber() &&
                    objreservation.getHour() == this.getHour() &&
                    objreservation.getNrOfPeople() == this.getNrOfPeople()
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
                "userId: " + date + "\n" + "date: " + date + "\n" + "table number: "+ tableNumber + "\n"
                + "hour: "+ hour + "\n" + "number of people: "+ nrOfPeople + "\n";
    }

    /*
    CREATE TABLE rezervare(
id NUMBER NOT NULL,
user_id NUMBER NOT NULL,
data DATE NOT NULL,
table_number NUMBER NOT NULL,
ora NUMBER NOT NULL,
nr_people NUMBER NOT NULL,
CONSTRAINT rezervare_pk PRIMARY KEY (id),
CONSTRAINT user_fk2 FOREIGN KEY (user_id) REFERENCES utilizator(id)
ON DELETE CASCADE
);
     */
}
