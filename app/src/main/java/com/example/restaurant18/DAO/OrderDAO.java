package com.example.restaurant18.DAO;

import android.util.Log;

import com.example.restaurant18.entity.Order;
import com.example.restaurant18.enums.OrderStatus;

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
        String statementQuery;
        if(order.getDescription() != null){
            statementQuery = "INSERT INTO comanda VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?)";
        } else{
            statementQuery = "INSERT INTO comanda VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?)";
        }

        PreparedStatement statement = connection.prepareStatement(statementQuery);
//        Log.v("test","test1992111");
        int id = getLastID();
        statement.setInt(1, id);
        order.setId(id);
        statement.setInt(2, order.getUserId());
        statement.setString(3, order.getDate());
        statement.setString(4, order.getStatus());
        statement.setString(5, order.getAddress());
        if(order.getDescription() != null){
            statement.setString(6, order.getDescription());
        } else{
            statement.setString(6, "");
        }

        try{
            statement.executeQuery();

            connection.commit();
        }catch (SQLException e){
//            Log.v("testOrder",e.getMessage());
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
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllDeliveringOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda WHERE status = " + OrderStatus.DELIVERING.getCode());
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllDoneOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda WHERE status = " + OrderStatus.DONE.getCode());
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllPlacedOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda WHERE status = " + OrderStatus.PLACED.getCode());
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllPreparingOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comanda WHERE status = " + OrderStatus.PREPARING.getCode());
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllDoneOrdersByUserId(int userId) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * FROM comanda WHERE status = " + OrderStatus.DONE.getCode() + " AND user_id = ? ";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Order order = new Order(0, resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
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
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
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
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
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
                    resultSet.getString(4), resultSet.getString(5));
            order.setId(resultSet.getInt(1));
            if(resultSet.getString(6) != null){
                order.setDescription(resultSet.getString(6));
            }
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

    public void deleteOrderByID(int id) throws SQLException {
        String query = "DELETE FROM comanda WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
        connection.commit();
    }

}
