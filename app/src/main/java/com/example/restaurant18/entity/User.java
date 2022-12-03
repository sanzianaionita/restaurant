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
    private String birthDate;
    private String appellative;

    public User(int id,String firstname, String lastname, String email, String password, String appellative,String birthDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.appellative = appellative;
        this.birthDate = birthDate;
    }

    public User(int id, String firstname, String lastname, String email, String password, String appellative, Date birthDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.appellative = appellative;

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.birthDate = simpleDateFormat.format(birthDate);
    }

    public User() {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAppellative() {
        return appellative;
    }

    public void setAppellative(String appellative) {
        this.appellative = appellative;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof User){
            User objuser = (User) obj;

            if (objuser.getFirstname().equals(this.getFirstname()) &&
                    objuser.getLastname().equals(this.getLastname()) &&
                    objuser.getEmail().equals(this.getEmail()) &&
                    objuser.getPassword().equals(this.getPassword()) &&
                    objuser.getAppellative().equals(this.getAppellative()) &&
                    objuser.getBirthDate().equals(this.getBirthDate())
            )
                return true;
            return false;
        }
        else return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "FirstName: " + firstname + "\n"+ "LastName: " + lastname + "\n" +
                "Email: " + email + "\n" + "Password: "+ password + "\n" +
                "Appellative: " + appellative + "\n" + "BirthDate: " + birthDate + "\n";
    }
}
