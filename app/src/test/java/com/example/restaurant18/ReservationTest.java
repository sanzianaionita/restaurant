package com.example.restaurant18;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.restaurant18.DAO.ReservationDAO;
import com.example.restaurant18.entity.Reservation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class ReservationTest {

    static Connection connection;
    static ReservationDAO reservationDAO;


    @BeforeAll
    public static void setup(){

        connection = mock(Connection.class);
        reservationDAO = new ReservationDAO(connection);
    }

    @Test
    public void createReservationTest_created() throws SQLException {

        String statementQuery = "INSERT INTO rezervare VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?,?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM rezervare")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);

        boolean response = reservationDAO.createReservation(new Reservation(1, "test", 1, 1, 1, "test"));

        Assertions.assertTrue(response);
    }

    @Test
    public void createReservationTest_notCreated() throws SQLException {
        String statementQuery = "INSERT INTO rezervare VALUES (?,?,TO_DATE(?,'DD-MM-YYYY'),?,?,?,?)";

        PreparedStatement preparedStatementMocked = mock(PreparedStatement.class);
        Statement statementMocked = mock(Statement.class);
        ResultSet resultSetMocked = mock(ResultSet.class);

        when(connection.prepareStatement(statementQuery)).thenReturn(preparedStatementMocked);
        when(connection.createStatement()).thenReturn(statementMocked);
        when(statementMocked.executeQuery("SELECT MAX(id) FROM rezervare")).thenReturn(resultSetMocked);
        when(resultSetMocked.getInt(any())).thenReturn(1);
        when(preparedStatementMocked.executeQuery()).thenThrow(new SQLException("test"));

        boolean response = reservationDAO.createReservation(new Reservation(1, "test", 1, 1, 1, "test"));

        Assertions.assertFalse(response);
    }

}
