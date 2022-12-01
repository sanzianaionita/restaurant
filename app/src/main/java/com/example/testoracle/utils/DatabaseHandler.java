package com.example.testoracle.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.68.180:1521:XE";
            //"jdbc:oracle:thin:@192.168.1.129:1521:XE";
    private static final String USERNAME = "CTXSYS";
    private static final String PASSWORD = "CTXSYS";

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
