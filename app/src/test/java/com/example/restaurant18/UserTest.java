package com.example.restaurant18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.entity.User;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserTest {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  static final String URL = "jdbc:oracle:thin:@192.168.100.34:1521:XE";
    private static final String USERNAME = "raisa";
    private static final String PASSWORD = "Sasakisan";

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
    public void createUserSuccessfulTest() throws SQLException {

        createConnection();

        User user = new User(100,"Ana", "Tofanel",
                "ceva@mail.com", "test",
                "mrs", "24-12-2000");

        UserDAO userDAO = new UserDAO(connection);

        userDAO.createUser(user);

        User selectedUser = userDAO.getUserByEmail("ceva@mail.com");

        assertEquals(user, selectedUser);
    }

    @Test
    public void createUserBrokeUniqueConstraintTest() throws SQLException {
        createConnection();

        User user = new User(1,"Raisa", "Tofanel",
                "rai@mail.com", "test",
                "mrs", "24-12-2000");

        UserDAO userDAO = new UserDAO(connection);

        assertFalse(userDAO.createUser(user));

        connection.close();
    }

    @Test
    public void GetUserByIDSuccessfulTest() throws SQLException {
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserByID(0);

        User userCorrect = new User(0,"admin", "admin",
                "admin@mail.com", "admin",
                "mrs", "24-12-2000");

        assertEquals(user, userCorrect);
        connection.close();
    }

    @Test
    public void GetUserByID_NonExistentID_Test() throws SQLException {
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserByID(400);
        assertEquals(user, null);
        connection.close();
    }

    @Test
    public void GetUserByNameSuccessfulTest() throws SQLException {
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        List<User> users = userDAO.getUserByName("Raisa", "Tofanel");
        assertNotEquals(users, null);
        connection.close();
    }

    @Test
    public void GetUserByName_NonExistentName_Test() throws SQLException {
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        List<User> users = userDAO.getUserByName("User", "NonExistent");
        assertTrue(users.isEmpty());
        connection.close();
    }

    /////facute de andrei
    @Test
    public void GetUserByEmailSuccessfulTest() throws SQLException {
        User userCorrect = new User(0,"admin", "admin",
                "admin@mail.com", "admin",
                "mrs", "24-12-2000");
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserByEmail("admin@mail.com");
        assertEquals(userCorrect , user);
        connection.close();
    }

    @Test
    public void GetUserByEmailFailedTest() throws SQLException {
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserByEmail("non_existin@mail.com");
        assertEquals(user, null);
        connection.close();
    }

    @Test
    public void EditUserTest() throws SQLException {
        String newFirstName = "Ioana";
        String newLastName = "Ionescu";
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        String fieldsToEdit[] = {"firstname", "lastname"};
        String[] values = {"Maria", "Miron"};
        userDAO.editUser(10, fieldsToEdit, values);

        User user = new User(10,"Maria", "Miron",
                "rai@mail.com", "test",
                "mrs", "24-12-2000");

        User selected = userDAO.getUserByEmail("rai@mail.com");

        assertEquals(user, selected);
    }
}