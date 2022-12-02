package com.example.restaurant18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseHandler {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.1.134:1521:XE";
    //"jdbc:oracle:thin:@192.168.1.129:1521:XE";
    private static final String USERNAME = "bogdan";
    private static final String PASSWORD = "12345";

    private static Connection connection;

    public static Connection createDbConn() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}