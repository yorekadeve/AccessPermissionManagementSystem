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

public class RolePermissionDAO {

    public List<RolePermission> getAllRolePermissions() {

        List<RolePermission> rolePermissions = new ArrayList<>();

        String sql = "SELECT role_id, permission_id FROM Role_Permissions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                RolePermission rolePermission = new RolePermission(
                        resultSet.getInt("role_id"),
                        resultSet.getInt("permission_id")
                );

                rolePermissions.add(rolePermission);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving role permissions: "
                    + e.getMessage());
        }

        return rolePermissions;
    }
    
        public String assignPermission(int roleId, int permissionId) {

        String existsSql =
                "SELECT COUNT(*) FROM Role_Permissions "
              + "WHERE role_id = ? AND permission_id = ?";

        String insertSql =
                "INSERT INTO Role_Permissions (role_id, permission_id) "
              + "VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                return "Database error: could not connect.";
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(existsSql)) {

                statement.setInt(1, roleId);
                statement.setInt(2, permissionId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        return "This role already has that permission.";
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, roleId);
                statement.setInt(2, permissionId);
                statement.executeUpdate();
            }

            return null;

        } catch (SQLException e) {

            System.out.println("Error assigning permission: " + e.getMessage());

            return "Database error: the permission could not be assigned.";
        }
    }

    public boolean removeRolePermission(int roleId, int permissionId) {

        String sql = "DELETE FROM Role_Permissions "
                   + "WHERE role_id = ? AND permission_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, roleId);
            statement.setInt(2, permissionId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error removing role permission: "
                    + e.getMessage());

            return false;
        }
    }
}
