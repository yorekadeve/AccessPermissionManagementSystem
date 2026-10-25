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

public class PermissionCheckDAO {

    private AccessLogDAO accessLogDAO = new AccessLogDAO();

    // Roles that count for a user today: roles whose dates are valid,
    // plus the junior roles they inherit through Role_Hierarchy.
    // Contains two "?" placeholders, both for the user id.
    private static final String EFFECTIVE_ROLES =
            "SELECT ur.role_id AS role_id "
          + "FROM User_Roles ur "
          + "WHERE ur.user_id = ? "
          + "AND (ur.start_date IS NULL OR ur.start_date <= CURDATE()) "
          + "AND (ur.end_date IS NULL OR ur.end_date >= CURDATE()) "
          + "UNION "
          + "SELECT rh.child_role_id AS role_id "
          + "FROM User_Roles ur "
          + "INNER JOIN Role_Hierarchy rh "
          + "ON rh.parent_role_id = ur.role_id "
          + "WHERE ur.user_id = ? "
          + "AND (ur.start_date IS NULL OR ur.start_date <= CURDATE()) "
          + "AND (ur.end_date IS NULL OR ur.end_date >= CURDATE())";

    public boolean hasPermission(int userId, int operationId, int resourceId) {

        String sql = "SELECT COUNT(*) "
                   + "FROM (" + EFFECTIVE_ROLES + ") er "
                   + "INNER JOIN Role_Permissions rp "
                   + "ON er.role_id = rp.role_id "
                   + "INNER JOIN Permissions p "
                   + "ON rp.permission_id = p.permission_id "
                   + "WHERE p.operation_id = ? "
                   + "AND p.resource_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, operationId);
            statement.setInt(4, resourceId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    boolean allowed = resultSet.getInt(1) > 0;

                    int sessionId =
                            new SessionDAO().getActiveSessionId(userId);

                    if (sessionId > 0) {
                        accessLogDAO.addAccessLog(
                                userId,
                                sessionId,
                                operationId,
                                resourceId,
                                null,
                                allowed ? "ALLOWED" : "DENIED"
                        );
                    }

                    return allowed;
                }
            }

        } catch (SQLException e) {
            System.out.println("Permission check error: "
                    + e.getMessage());
        }

        return false;
    }

    public boolean hasPermission(int userId,
                                 String operationName,
                                 String resourceName) {

        String sql = "SELECT p.operation_id, p.resource_id, COUNT(*) "
                   + "FROM (" + EFFECTIVE_ROLES + ") er "
                   + "INNER JOIN Role_Permissions rp "
                   + "ON er.role_id = rp.role_id "
                   + "INNER JOIN Permissions p "
                   + "ON rp.permission_id = p.permission_id "
                   + "INNER JOIN Operations o "
                   + "ON p.operation_id = o.operation_id "
                   + "INNER JOIN Resources r "
                   + "ON p.resource_id = r.resource_id "
                   + "WHERE o.operation_name = ? "
                   + "AND r.resource_name = ? "
                   + "GROUP BY p.operation_id, p.resource_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setString(3, operationName);
            statement.setString(4, resourceName);

            try (ResultSet resultSet = statement.executeQuery()) {

                int operationId = 0;
                int resourceId = 0;
                boolean allowed = false;

                if (resultSet.next()) {
                    operationId = resultSet.getInt("operation_id");
                    resourceId = resultSet.getInt("resource_id");
                    allowed = resultSet.getInt(3) > 0;
                } else {

                    String idSql = "SELECT o.operation_id, r.resource_id "
                                 + "FROM Operations o, Resources r "
                                 + "WHERE o.operation_name = ? "
                                 + "AND r.resource_name = ?";

                    try (PreparedStatement idStatement =
                            connection.prepareStatement(idSql)) {

                        idStatement.setString(1, operationName);
                        idStatement.setString(2, resourceName);

                        try (ResultSet idResult =
                                idStatement.executeQuery()) {

                            if (idResult.next()) {
                                operationId =
                                        idResult.getInt("operation_id");
                                resourceId =
                                        idResult.getInt("resource_id");
                            }
                        }
                    }
                }

                int sessionId =
                        new SessionDAO().getActiveSessionId(userId);

                if (sessionId > 0 && operationId > 0 && resourceId > 0) {
                    accessLogDAO.addAccessLog(
                            userId,
                            sessionId,
                            operationId,
                            resourceId,
                            null,
                            allowed ? "ALLOWED" : "DENIED"
                    );
                }

                return allowed;
            }

        } catch (SQLException e) {
            System.out.println("Permission check error: "
                    + e.getMessage());
        }

        return false;
    }
}
