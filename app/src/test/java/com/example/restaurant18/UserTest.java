package com.example.restaurant18;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.entity.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTest {

    static Connection connection;
    static UserDAO userDAO;

    @BeforeAll
    public static void setup(){
        connection = Mockito.mock(Connection.class);
        userDAO = new UserDAO(connection);
    }

    @Test
    public void testCreateUser_created() throws SQLException {
        String statementQuery = "INSERT INTO utilizator VALUES (?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'), ?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM utilizator")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);

        boolean response = userDAO.createUser(new User("test", "test", "test@test.com", "test", "test", "test"));

        Assertions.assertTrue(response);

    }

    @Test
    public void testCreateUser_notCreated() throws SQLException {
        String statementQuery = "INSERT INTO utilizator VALUES (?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'), ?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM utilizator")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);
        when(preparedStatementMocked.executeUpdate()).thenThrow(new SQLException("test"));

        boolean response = userDAO.createUser(new User("test", "test", "test@test.com", "test", "test", "test"));

        Assertions.assertFalse(response);

    }
}