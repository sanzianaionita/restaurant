package com.example.restaurant18.DAO;

import android.util.Log;

import com.example.restaurant18.OrderComponent;
import com.example.restaurant18.entity.Order;
import com.example.restaurant18.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderProductDAO {

    private Connection connection;

    public OrderProductDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createOrderProduct(ArrayList<OrderComponent> orderProductsList, Order order) throws SQLException {
        String statementQuery;
        statementQuery = "INSERT INTO comandaprodus VALUES (?,?,?)";

        for (OrderComponent orderComponent:
             orderProductsList) {
            Product product = orderComponent.getOrderProduct();
            int quantity = orderComponent.getOrderProductQuantity();

            PreparedStatement statement = connection.prepareStatement(statementQuery);

            statement.setInt(1, order.getId());
            statement.setInt(2, product.getId());
            statement.setInt(3, quantity);

            try{
                statement.executeQuery();

                connection.commit();
            }catch (SQLException e){
                Log.v("testOrderProduct",e.getMessage());
                return false;
            }
        }

        return true;
    }
}
