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

public class AccessReviewDAO {

    public List<String[]> getPermissionUsageReview() {

        List<String[]> reviewRecords = new ArrayList<>();

        String sql =
                "SELECT "
                + "u.user_id, "
                + "u.full_name, "
                + "r.role_id, "
                + "r.role_name, "
                + "p.permission_id, "
                + "o.operation_name, "
                + "res.resource_name, "
                + "p.description AS permission_description, "
                + "MAX( "
                + "    CASE "
                + "        WHEN al.result = 'ALLOWED' "
                + "        THEN al.action_time "
                + "        ELSE NULL "
                + "    END "
                + ") AS last_used, "
                + "CASE "
                + "    WHEN MAX( "
                + "        CASE "
                + "            WHEN al.result = 'ALLOWED' "
                + "            THEN al.action_time "
                + "            ELSE NULL "
                + "        END "
                + "    ) IS NULL "
                + "    THEN 'UNUSED' "
                + "    ELSE 'USED' "
                + "END AS usage_status "
                + "FROM Users u "
                + "JOIN User_Roles ur "
                + "    ON u.user_id = ur.user_id "
                + "JOIN Roles r "
                + "    ON ur.role_id = r.role_id "
                + "JOIN Role_Permissions rp "
                + "    ON r.role_id = rp.role_id "
                + "JOIN Permissions p "
                + "    ON rp.permission_id = p.permission_id "
                + "JOIN Operations o "
                + "    ON p.operation_id = o.operation_id "
                + "JOIN Resources res "
                + "    ON p.resource_id = res.resource_id "
                + "LEFT JOIN Access_Logs al "
                + "    ON al.user_id = u.user_id "
                + "    AND al.operation_id = p.operation_id "
                + "    AND al.resource_id = p.resource_id "
                + "    AND al.action_time >= DATE_SUB(NOW(), INTERVAL 6 MONTH) "
                + "GROUP BY "
                + "u.user_id, "
                + "u.full_name, "
                + "r.role_id, "
                + "r.role_name, "
                + "p.permission_id, "
                + "o.operation_name, "
                + "res.resource_name, "
                + "p.description "
                + "ORDER BY "
                + "u.full_name, "
                + "r.role_name, "
                + "usage_status, "
                + "o.operation_name, "
                + "res.resource_name";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                String[] record = new String[10];

                record[0] =
                        String.valueOf(
                                resultSet.getInt("user_id")
                        );

                record[1] =
                        resultSet.getString("full_name");

                record[2] =
                        String.valueOf(
                                resultSet.getInt("role_id")
                        );

                record[3] =
                        resultSet.getString("role_name");

                record[4] =
                        String.valueOf(
                                resultSet.getInt("permission_id")
                        );

                record[5] =
                        resultSet.getString("operation_name");

                record[6] =
                        resultSet.getString("resource_name");

                record[7] =
                        resultSet.getString(
                                "permission_description"
                        );

                record[8] =
                        resultSet.getString("last_used");

                record[9] =
                        resultSet.getString("usage_status");

                reviewRecords.add(record);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving permission usage review: "
                    + e.getMessage()
            );
        }

        return reviewRecords;
    }
    
    public boolean logAccessRemoval(
        int userId,
        int sessionId,
        String targetRecord) {

    String sql =
            "INSERT INTO Access_Logs "
            + "(user_id, session_id, operation_id, resource_id, "
            + "target_record, action_time, result) "
            + "SELECT ?, ?, o.operation_id, r.resource_id, "
            + "?, NOW(), 'ALLOWED' "
            + "FROM Operations o "
            + "JOIN Resources r "
            + "ON r.resource_name = 'Access Reviews' "
            + "WHERE o.operation_name = 'MANAGE'";

    try (Connection connection =
                 DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);
        statement.setInt(2, sessionId);
        statement.setString(3, targetRecord);

        int rowsInserted =
                statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {

        System.out.println(
                "Error logging access removal: "
                + e.getMessage()
        );

        return false;
    }
}
    
}
