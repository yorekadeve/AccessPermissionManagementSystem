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

public class OperationDAO {

    public List<Operation> getAllOperations() {

        List<Operation> operations = new ArrayList<>();

        String sql = "SELECT operation_id, operation_name, description "
                   + "FROM Operations";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Operation operation = new Operation(
                        resultSet.getInt("operation_id"),
                        resultSet.getString("operation_name"),
                        resultSet.getString("description")
                );

                operations.add(operation);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving operations: "
                    + e.getMessage());
        }

        return operations;
    }
    
    public boolean addOperation(Operation operation) {

    String sql = "INSERT INTO Operations "
               + "(operation_name, description) "
               + "VALUES (?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, operation.getOperationName());
        statement.setString(2, operation.getDescription());

        int rowsInserted = statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {

        System.out.println("Error adding operation: "
                + e.getMessage());

        return false;
    }
}

public boolean updateOperation(Operation operation) {

    String sql = "UPDATE Operations "
               + "SET operation_name = ?, description = ? "
               + "WHERE operation_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, operation.getOperationName());
        statement.setString(2, operation.getDescription());
        statement.setInt(3, operation.getOperationId());

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {

        System.out.println("Error updating operation: "
                + e.getMessage());

        return false;
    }
}

public boolean deleteOperation(int operationId) {

    String sql = "DELETE FROM Operations "
               + "WHERE operation_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, operationId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {

        System.out.println("Error deleting operation: "
                + e.getMessage());

        return false;
    }
}
}
