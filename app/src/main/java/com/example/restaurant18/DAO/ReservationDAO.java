package com.example.restaurant18.DAO;

import android.util.Log;

import com.example.restaurant18.entity.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    private int getLastID() throws SQLException {
        int id;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM rezervare");
        resultSet.next();
        id = resultSet.getInt(1);
        id = id + 1;
        return id;
    }

    //create an order in the database given an object of type order as a parameter
    public boolean createReservation(Reservation reservation) throws SQLException {
        String statementQuery;
        if(reservation.getDetails() != null){
            statementQuery = "INSERT INTO rezervare VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?,?)";
        } else{
            statementQuery = "INSERT INTO rezervare VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?)";
        }


        PreparedStatement statement = connection.prepareStatement(statementQuery);
        Log.v("test","test1992111");
        int id = getLastID();
        statement.setInt(1, id);
        statement.setInt(2, reservation.getUserId());
        statement.setString(3, reservation.getDate());
        statement.setInt(4, reservation.getTableNumber());
        statement.setInt(5, reservation.getHour());
        statement.setInt(6, reservation.getNrOfPeople());
        if(reservation.getDetails() != null){
            statement.setString(7, reservation.getDetails());
        }
        try{
            statement.executeQuery();

            connection.commit();
        }catch (SQLException e){
            Log.v("test",e.getMessage());
            return false;
        }
        return true;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM rezervare");
        while (resultSet.next()){
            Reservation reservation = new Reservation(resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
            reservation.setId(resultSet.getInt(1));
            if(resultSet.getString(7) != null){
                reservation.setDetails(resultSet.getString(7));
            }
            reservationList.add(reservation);
        }
        return reservationList;
    }

    public List<Reservation> getReservationsByDate(String date) throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM rezervare WHERE data = TO_DATE(?,'DD-MM-YYYY')";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, date);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Reservation reservation = new Reservation(resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
            reservation.setId(resultSet.getInt(1));
            if(resultSet.getString(7) != null){
                reservation.setDetails(resultSet.getString(7));
            }
            reservationList.add(reservation);
        }
        return reservationList;
    }

    public List<Reservation> getReservationsByUserId(int userId) throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM rezervare WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Reservation reservation = new Reservation(resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
            reservation.setId(resultSet.getInt(1));
            if(resultSet.getString(7) != null){
                reservation.setDetails(resultSet.getString(7));
            }
            reservationList.add(reservation);
        }
        return reservationList;
    }

    public Reservation getReservationById(int id) throws SQLException {
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM rezervare WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery(query);
        Reservation reservation = new Reservation(resultSet.getInt(2), resultSet.getString(3),
                resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
        reservation.setId(resultSet.getInt(1));
        if(resultSet.getString(7) != null){
            reservation.setDetails(resultSet.getString(7));
        }

        return reservation;
    }

    public void editReservation(int id, String[] fieldsToUpdate, String values[]) throws SQLException {

        String query = "UPDATE rezervare SET ";
        for (int i = 0; i < fieldsToUpdate.length; i++){
            String field = fieldsToUpdate[i];
            String value = values[i];
            query += i != fieldsToUpdate.length - 1 ? field + " = " + value + ", " : field + " = " + value;
        }

        PreparedStatement statement = connection.prepareStatement(query);

        statement.executeUpdate();
        connection.commit();
    }

    public void deleteReservationByID(int id) throws SQLException {
        String query = "DELETE FROM rezervare WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
        connection.commit();
    }

    public void deleteReservationByUserID(int userId) throws SQLException {
        String query = "DELETE FROM rezervare WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);

        statement.executeUpdate();
        connection.commit();
    }
}
