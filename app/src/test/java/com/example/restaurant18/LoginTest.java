package com.example.restaurant18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.entity.User;
import com.example.restaurant18.ui.login.LoginFragment;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class LoginTest {
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
    public void retrieveUserByEmailAndPasswordTest() throws SQLException {
        createConnection();

        UserDAO userDAO = new UserDAO(connection);
        User userExtracted = userDAO.getUserByCredentials("admin@mail.com", "admin");

        User correctUser = new User(0, "admin", "admin", "admin@mail.com",
                "admin","mrs","24-12-2000");

        assertEquals(userExtracted, correctUser);
    }
    

}