package com.sunrise.dental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sunrise_dental";
    private static final String User = "root";
    private static final String Pass = "admin";


    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, User, Pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
