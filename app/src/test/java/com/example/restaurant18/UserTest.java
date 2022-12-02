package com.example.restaurant18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.restaurant18.DAO.UserDAO;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserTest {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  static final String URL = "jdbc:oracle:thin:@192.168.100.17:1521:XE";
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

        User user = new User("Raisa", "Tofanel",
                "raisatest2@gmail.com", "testRaisaPassword", "24-12-2000");

        UserDAO userDAO = new UserDAO(connection);

        userDAO.createUser(user);

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM utilizator WHERE username = '" + user.getUsername() + "'");

        rs.next();

        User selectedUser = new User(rs.getString(2),rs.getString(3), rs.getString(4),
                rs.getString(5),rs.getString(6), rs.getDate(7));

        assertTrue(user.equals(selectedUser));
    }

    @Test
    public void createUserBrokeUniqueConstraintTest() throws SQLException {
        createConnection();

        User user = new User("raisa1234", "Raisa", "Tofanel",
                "raisatest@gmail.com", "testRaisaPassword", "24-12-2000");

        UserDAO userDAO = new UserDAO(connection);

        assertFalse(userDAO.createUser(user));

        connection.close();
    }

    @Test
    public void GetUserByIDSuccessfulTest() throws SQLException {
        createConnection();
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserByID(4);

        User userCorrect = new User("raisa1234", "Raisa", "Tofanel",
                "raisatest@gmail.com", "testRaisaPassword", "24-12-2000");

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
}