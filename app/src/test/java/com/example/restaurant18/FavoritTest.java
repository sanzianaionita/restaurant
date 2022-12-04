package com.example.restaurant18;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.restaurant18.DAO.FavoritDAO;
import com.example.restaurant18.DAO.OrderDAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FavoritTest {

    static FavoritDAO favoritDAO;
    static Connection connection;

    @BeforeAll
    public static void setup(){

        connection = mock(Connection.class);
        favoritDAO = new FavoritDAO(connection);

    }

    @Test
    public void testInsertFavoriteProduct_inserted() throws SQLException {

        String statementQuery = "insert into favorit values(?, ?, ?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM favorit")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);

        boolean result = favoritDAO.insertFavoriteProduct(1, 1);

        Assertions.assertTrue(result);
    }

    @Test
    public void testInsertFavoriteProduct_notInserted() throws SQLException {

        String statementQuery = "insert into favorit values(?, ?, ?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM favorit")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);
        when(preparedStatementMocked.executeUpdate()).thenThrow(new SQLException("test"));

        boolean result = favoritDAO.insertFavoriteProduct(1, 1);

        Assertions.assertFalse(result);
    }

}
