package com.sunrise.dental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User login(String username, String password) {

        String sql = "SELECT username, role FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new User(resultSet.getString("username"), null, resultSet.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}