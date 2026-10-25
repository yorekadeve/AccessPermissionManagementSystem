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

public class RoleDAO {

    public List<Role> getAllRoles() {

        List<Role> roles = new ArrayList<>();

        String sql = "SELECT role_id, role_name, description FROM Roles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Role role = new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name"),
                        resultSet.getString("description")
                );

                roles.add(role);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving roles: " + e.getMessage());
        }

        return roles;
    }
    
    public boolean addRole(Role role) {

    String sql = "INSERT INTO Roles (role_name, description) "
               + "VALUES (?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, role.getRoleName());
        statement.setString(2, role.getDescription());

        int rowsInserted = statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {

        System.out.println("Error adding role: "
                + e.getMessage());

        return false;
    }
}
    
    public boolean updateRole(Role role) {

    String sql = "UPDATE Roles "
               + "SET role_name = ?, description = ? "
               + "WHERE role_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, role.getRoleName());
        statement.setString(2, role.getDescription());
        statement.setInt(3, role.getRoleId());

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {

        System.out.println("Error updating role: "
                + e.getMessage());

        return false;
    }
}
    
    public boolean deleteRole(int roleId) {

    String sql = "DELETE FROM Roles WHERE role_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, roleId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {

        System.out.println("Error deleting role: "
                + e.getMessage());

        return false;
    }
}
}
