package com.example.restaurant18.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String date_of_birth;

    public User(String firstname, String lastname, String email, String password, String date_of_birth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
    }

    public User(String firstname, String lastname, String email, String password, Date date_of_birth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        this.date_of_birth = simpleDateFormat.format(date_of_birth);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof User){
            User objuser = (User) obj;

            if (objuser.getFirstname().equals(this.getFirstname()) &&
                    objuser.getLastname().equals(this.getLastname()) &&
                    objuser.getEmail().equals(this.getEmail()) &&
                    objuser.getPassword().equals(this.getPassword()) &&
                    objuser.getDate_of_birth().equals(this.getDate_of_birth())
            )
                return true;
            return false;
        }
        else return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "FirstName:" + firstname + "\n"+
                "LastName:" + lastname + "\n" + "Email:" + email + "\n" + "Password:"+
                password + "\n" + "DateOfBirth" + date_of_birth + "\n";
    }
}
