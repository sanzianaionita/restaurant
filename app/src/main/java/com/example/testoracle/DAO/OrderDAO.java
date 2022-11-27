package com.example.testoracle.DAO;

import android.util.Log;

import com.example.testoracle.entity.Order;
import com.example.testoracle.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    //private int id;
    //private int userId;
    //private String date;
    //private String status; // ENUM
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    private int getLastID() throws SQLException {
        int id;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM comanda");
        resultSet.next();
        id = resultSet.getInt(1);
        id = id + 1;
        return id;
    }

    //create an order in the database given an object of type order as a parameter
    public boolean createOrder(Order order) throws SQLException {
        String statementQuery = "INSERT INTO comanda VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?)";

        PreparedStatement statement = connection.prepareStatement(statementQuery);
        Log.v("test","test1992111");
        int id = getLastID();
        int tt = 120;
        statement.setInt(1, tt);
        statement.setInt(2, order.getUserId());
        statement.setString(3, order.getDate());
        statement.setString(4, order.getStatus());
        statement.setString(5, order.getAddress());
        statement.setString(6, order.getDescription());
        Log.v("test19",String.valueOf(order.getUserId())+String.valueOf(order.getUserId())+order.getStatus()+order.getDate()+order.getAddress()+order.getDescription()) ;
        Log.v("test","test19922222");
        try{
            Log.v("test","test1992yuyuy");
            statement.executeQuery();
            Log.v("test","test1992yuyuy2");

            connection.commit();
            Log.v("test","test1992uuu");
        }catch (SQLException e){
            Log.v("test",e.getMessage());
            return false;
        }
        return true;
    }


    public List<Order> getAllOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda");
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllDeliveringOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda WHERE status = 'delivering'");
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllDoneOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda WHERE status = 'done'");
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllDoneOrdersByUserId(int userId) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM comanda WHERE status = 'done' AND user_id = ? ";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            orderList.add(order);
        }
        return orderList;
    }

    public Order getOrderByID(int id) throws SQLException {
        Order order = null;

        String query = "SELECT * FROM comanda WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            break;
        }
        return order;
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM comanda WHERE user_id = ? ";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            orderList.add(order);
        }
        return orderList;
    }
    // TO DO : CAZ DATA
    public List<Order> getOrdersByDate(String date) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM comanda WHERE user_id = ? ";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, date);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            order.setId(resultSet.getInt(1));
            orderList.add(order);
        }
        return orderList;
    }

    public void editOrder(int id, String[] fieldsToUpdate, String values[]) throws SQLException {

        String query = "UPDATE comanda SET ";
        for (int i = 0; i < fieldsToUpdate.length; i++){
            String field = fieldsToUpdate[i];
            String value = values[i];
            query += i != fieldsToUpdate.length - 1 ? field + " = " + value + ", " : field + " = " + value;
        }

        PreparedStatement statement = connection.prepareStatement(query);

        statement.executeUpdate();
        connection.commit();
    }

    public void deleteUserByID(int id) throws SQLException {
        String query = "DELETE FROM comanda WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
        connection.commit();
    }

}
