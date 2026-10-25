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

public class AccessLogDAO {

    public List<AccessLog> getAllAccessLogs() {

        List<AccessLog> accessLogs = new ArrayList<>();

        String sql = "SELECT log_id, user_id, session_id, operation_id, "
                   + "resource_id, target_record, action_time, result "
                   + "FROM Access_Logs";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                AccessLog accessLog = new AccessLog(
                        resultSet.getInt("log_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("session_id"),
                        resultSet.getInt("operation_id"),
                        resultSet.getInt("resource_id"),
                        resultSet.getString("target_record"),
                        resultSet.getString("action_time"),
                        resultSet.getString("result")
                );

                accessLogs.add(accessLog);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving access logs: "
                    + e.getMessage());
        }

        return accessLogs;
    }
    
        public boolean addAccessLog(int userId,
                                int sessionId,
                                int operationId,
                                int resourceId,
                                String targetRecord,
                                String result) {

        String sql = "INSERT INTO Access_Logs "
                   + "(user_id, session_id, operation_id, resource_id, "
                   + "target_record, action_time, result) "
                   + "VALUES (?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, sessionId);
            statement.setInt(3, operationId);
            statement.setInt(4, resourceId);
            statement.setString(5, targetRecord);
            statement.setString(6, result);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding access log: "
                    + e.getMessage());
            return false;
        }
    }
}
