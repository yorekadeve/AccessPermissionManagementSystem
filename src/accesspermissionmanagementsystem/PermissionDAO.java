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

public class PermissionDAO {

    public List<Permission> getAllPermissions() {

        List<Permission> permissions = new ArrayList<>();

        String sql = "SELECT permission_id, operation_id, resource_id, description "
                   + "FROM Permissions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Permission permission = new Permission(
                        resultSet.getInt("permission_id"),
                        resultSet.getInt("operation_id"),
                        resultSet.getInt("resource_id"),
                        resultSet.getString("description")
                );

                permissions.add(permission);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving permissions: " + e.getMessage());
        }

        return permissions;
    }
    
    public boolean addPermission(Permission permission) {

    String sql = "INSERT INTO Permissions "
               + "(operation_id, resource_id, description) "
               + "VALUES (?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, permission.getOperationId());
        statement.setInt(2, permission.getResourceId());
        statement.setString(3, permission.getDescription());

        int rowsInserted = statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {

        System.out.println("Error adding permission: "
                + e.getMessage());

        return false;
    }
}
    
    public boolean updatePermission(Permission permission) {

    String sql = "UPDATE Permissions "
               + "SET operation_id = ?, resource_id = ?, description = ? "
               + "WHERE permission_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, permission.getOperationId());
        statement.setInt(2, permission.getResourceId());
        statement.setString(3, permission.getDescription());
        statement.setInt(4, permission.getPermissionId());

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {

        System.out.println("Error updating permission: "
                + e.getMessage());

        return false;
    }
}
    
    public boolean deletePermission(int permissionId) {

    String sql = "DELETE FROM Permissions WHERE permission_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, permissionId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {

        System.out.println("Error deleting permission: "
                + e.getMessage());

        return false;
    }
}
}
