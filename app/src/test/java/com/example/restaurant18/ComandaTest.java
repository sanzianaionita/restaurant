package com.example.restaurant18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.restaurant18.DAO.OrderDAO;
import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.entity.Order;
import com.example.restaurant18.entity.User;
import com.example.restaurant18.enums.OrderStatus;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComandaTest {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  static final String URL = "jdbc:oracle:thin:@192.168.0.178:1521:XE";
    private static final String USERNAME = "CTXSYS";
    private static final String PASSWORD = "CTXSYS";

    private Connection connection;

    private void createConnection(){
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createSuccessfullOrder() throws SQLException {
        createConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        Order order = new Order(1,1, "10-10-2022",
                OrderStatus.PLACED.getCode(), "Adresa de test", "Descriere de test");

        assertTrue(orderDAO.createOrder(order));
    }

    @Test
    public void createFailOrder_NonExistentUserID() throws SQLException {
        createConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        Order order = new Order(1,999, "10-10-2022",
                OrderStatus.PLACED.getCode(), "Adresa de test", "Descriere de test");

        assertFalse(orderDAO.createOrder(order));
    }

    @Test
    public void createFailOrder_DuplicateOrderID() throws SQLException {
        createConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        Order order = new Order(1,1, "11-11-2022",
                OrderStatus.PLACED.getCode(), "Adresa2", "Descriere2");

        assertFalse(orderDAO.createOrder(order));
    }

    @Test
    public void getOrderByIDSuccessfullOrder() throws SQLException {
        createConnection();
        OrderDAO orderDAO = new OrderDAO(connection);

        Order order = orderDAO.getOrderByID(1);
        Order correctOrder = new Order(1,1, "11-11-2022",
                OrderStatus.PLACED.getCode(), "Adresa2", "Descriere2");

        assertEquals(order, correctOrder);
    }

    @Test
    public void getOrderByIDFail_WrongIDOrder() throws SQLException {
        createConnection();
        OrderDAO orderDAO = new OrderDAO(connection);

        Order order = orderDAO.getOrderByID(999);
        assertEquals(order, null);
    }

    @Test
    public void getOrderByUserID_Successfull() throws SQLException {
        createConnection();;
        OrderDAO orderDAO = new OrderDAO(connection);

        List<Order> orders = orderDAO.getOrdersByUserId(1);

        Order correctOrder = new Order(1,1, "11-11-2022",
                OrderStatus.PLACED.getCode(), "Adresa2", "Descriere2");

        List<Order> correctOrders = new ArrayList<>();

        correctOrders.add(correctOrder);
        assertEquals(correctOrders, orders);
    }

    @Test
    public void getOrderByUserID_Fail_WrongID() throws SQLException {
        createConnection();;
        OrderDAO orderDAO = new OrderDAO(connection);

        List<Order> orders = orderDAO.getOrdersByUserId(999);
        assertEquals(null, orders);
    }


    @Test
    public void getOrderByDate_Successfull() throws SQLException {
        createConnection();;
        OrderDAO orderDAO = new OrderDAO(connection);

        List<Order> orders = orderDAO.getOrdersByDate("11-11-2022");

        Order correctOrder = new Order(1,1, "11-11-2022",
                OrderStatus.PLACED.getCode(), "Adresa2", "Descriere2");

        List<Order> correctOrders = new ArrayList<>();

        correctOrders.add(correctOrder);
        assertEquals(correctOrders, orders);
    }

    @Test
    public void getOrderByUserDate_Fail_WrongID() throws SQLException {
        createConnection();;
        OrderDAO orderDAO = new OrderDAO(connection);

        List<Order> orders = orderDAO.getOrdersByDate("03-03-2023");
        assertEquals(null, orders);
    }

    @Test
    public void editOrderTest() throws SQLException {
        createConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        String fieldsToEdit[] = {"status"};
        String[] values = {OrderStatus.DONE.getCode()};

        orderDAO.editOrder(1, fieldsToEdit, values);
        Order order = orderDAO.getOrderByID(1);
        Order correctOrder = new Order(1,1, "11-11-2022",
                OrderStatus.PLACED.getCode(), "Adresa2", "Descriere2");

        assertEquals(order, correctOrder);
    }
}
