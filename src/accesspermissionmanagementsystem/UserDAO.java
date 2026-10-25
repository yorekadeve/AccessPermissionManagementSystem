/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT user_id, full_name, email, status FROM Users";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("status")
                );

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }

        return users;
    }
    
    public int addUser(User user) {

    String sql = "INSERT INTO Users (full_name, email, status) "
               + "VALUES (?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(
                         sql,
                         java.sql.Statement.RETURN_GENERATED_KEYS)) {

        statement.setString(1, user.getFullName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getStatus());

        int rowsInserted = statement.executeUpdate();

        if (rowsInserted > 0) {

            try (ResultSet resultSet =
                         statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }

    } catch (SQLException e) {

        System.out.println(
                "Error adding user: "
                + e.getMessage()
        );
    }

    return -1;
}
    
    public int getUserId(String email) {

    String sql =
            "SELECT user_id FROM Users WHERE email = ?";

    try (Connection connection =
                 DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setString(1, email);

        try (ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }

        }

    } catch (SQLException e) {

        System.out.println(
                "Error getting user ID: "
                + e.getMessage()
        );
    }

    return -1;
}
    
    public boolean updateUser(User user) {

    String sql = "UPDATE Users "
               + "SET full_name = ?, email = ?, status = ? "
               + "WHERE user_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, user.getFullName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getStatus());
        statement.setInt(4, user.getUserId());

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {

        System.out.println("Error updating user: "
                + e.getMessage());

        return false;
    }
}
    
    public boolean deleteUser(int userId) {

    String sql = "DELETE FROM Users WHERE user_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {

        System.out.println("Error deleting user: "
                + e.getMessage());

        return false;
    }
}
}
