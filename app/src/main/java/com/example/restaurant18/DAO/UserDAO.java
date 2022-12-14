package com.example.restaurant18.DAO;

import com.example.restaurant18.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//unde pun query urile
public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public int getLastID() throws SQLException {
        int id;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM utilizator");
        resultSet.next();
        id = resultSet.getInt(1);
        id = id + 1;
        return id;
    }

    //create a user in the database given an object of type user as a parameter
    public boolean createUser(User user) throws SQLException {

        String statementQuery = "INSERT INTO utilizator VALUES (?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'),?)";

        PreparedStatement statement = connection.prepareStatement(statementQuery);

        int id = getLastID();
        statement.setInt(1, id);

        statement.setString(2, user.getFirstname());
        statement.setString(3, user.getLastname());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPassword());
        statement.setString(7, user.getAppellative());
        statement.setString(6, user.getBirthDate());

        try {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean createGuestUser(User user) throws SQLException {

        String statementQuery = "INSERT INTO utilizator VALUES (?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'),?)";

        PreparedStatement statement = connection.prepareStatement(statementQuery);

        statement.setInt(1, 998);

        statement.setString(2, user.getFirstname());
        statement.setString(3, user.getLastname());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPassword());
        statement.setString(7, user.getAppellative());
        statement.setString(6, user.getBirthDate());

        try {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    //get a list of all users in the database
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Statement statement = connection.createStatement();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM utilizator");
        while (resultSet.next()) {
            User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(7), resultSet.getDate(6));
            userList.add(user);
        }
        return userList;
    }

    public User getUserByID(int ID) throws SQLException {
        User user = null;

        String query = "SELECT * FROM utilizator WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(7), resultSet.getDate(6));
            break;
        }
        return user;
    }

    public List<User> getUserByName(String firstName, String lastName) throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM utilizator WHERE firstname = ? and lastname = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(7), resultSet.getDate(6));
            users.add(user);
            break;
        }
        return users;
    }

    public User getUserByEmail(String email) throws SQLException {
        User user = null;

        String query = "SELECT * FROM utilizator WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(7), resultSet.getDate(6));
        }
        return user;
    }

    public void editUser(int ID, String[] fieldsToUpdate, String values[]) throws SQLException {

        String query = "UPDATE utilizator SET ";
        for (int i = 0; i < fieldsToUpdate.length; i++) {
            String field = fieldsToUpdate[i];
            String value = values[i];
            if (!field.equals("date_of_birth")) {
                query += field + " = '" + value + "'";
            } else {
                query += field + " = TO_DATE('" + value + "', 'dd-MM-yyyy')";
            }

            if (i != fieldsToUpdate.length - 1) {
                query += ", ";
            }
        }


        PreparedStatement statement = connection.prepareStatement(query);

        statement.executeUpdate();
        connection.commit();
    }

    public void deleteUserByID(int ID) throws SQLException {
        String query = "DELETE FROM utilizator WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ID);

        statement.executeUpdate();
        connection.commit();
    }

    public void deleteUsersByName(String firstName, String lastName) throws SQLException {
        String query = "DELETE FROM utilizator WHERE firstname = ? AND lastname = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);

        statement.executeUpdate();
        connection.commit();
    }

    public User getUserByCredentials(String email, String password) throws SQLException {

        User user;
        String query = "SELECT * FROM utilizator WHERE email = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5),
                resultSet.getString(7), resultSet.getDate(6));

        return user;
    }

}
