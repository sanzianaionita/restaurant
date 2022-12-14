package com.example.restaurant18;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.util.Log;

import com.example.restaurant18.DAO.OrderDAO;
import com.example.restaurant18.entity.Order;
import com.example.restaurant18.entity.Reservation;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComandaTest {

    static OrderDAO orderDAO;
    static Connection connection;

    @BeforeAll
    public static void setup(){
        connection = mock(Connection.class);
        orderDAO = new OrderDAO(connection);
    }

    @Test
    public void testCreateOrder_created() throws SQLException {

        String statementQuery = "INSERT INTO comanda VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM comanda")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);

        boolean response = orderDAO.createOrder(new Order(1, "test", "test", "test", "test"));

        Assertions.assertTrue(response);
    }

    @Test
    public void testCreateOrder_notCreated() throws SQLException {

        String statementQuery = "INSERT INTO comanda VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM comanda")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);
        when(preparedStatementMocked.executeQuery()).thenThrow(new SQLException("test"));

        boolean response = orderDAO.createOrder(new Order(1, "test", "test", "test", "test"));

        Assertions.assertFalse(response);
    }
}
