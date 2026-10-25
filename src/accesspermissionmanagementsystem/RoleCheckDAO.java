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

public class RoleCheckDAO {

    public List<Role> getUserRoles(int userId) {

        List<Role> roles = new ArrayList<>();

        String sql = "SELECT r.role_id, r.role_name, r.description "
                   + "FROM Roles r "
                   + "INNER JOIN User_Roles ur "
                   + "ON r.role_id = ur.role_id "
                   + "WHERE ur.user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Role role = new Role(
                            resultSet.getInt("role_id"),
                            resultSet.getString("role_name"),
                            resultSet.getString("description")
                    );

                    roles.add(role);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user roles: "
                    + e.getMessage());
        }

        return roles;
    }
}
