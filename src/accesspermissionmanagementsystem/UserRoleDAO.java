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

public class UserRoleDAO {

    public List<UserRole> getAllUserRoles() {

        List<UserRole> userRoles = new ArrayList<>();

        String sql = "SELECT user_id, role_id FROM User_Roles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                UserRole userRole = new UserRole(
                        resultSet.getInt("user_id"),
                        resultSet.getInt("role_id")
                );

                userRoles.add(userRole);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user roles: "
                    + e.getMessage());
        }

        return userRoles;
    }
    
    public boolean removeUserRole(int userId, int roleId) {

    String sql = "DELETE FROM User_Roles "
               + "WHERE user_id = ? "
               + "AND role_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);
        statement.setInt(2, roleId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {

        System.out.println("Error removing user role: "
                + e.getMessage());

        return false;
    }
}
    
        public String assignRole(int userId, int roleId) {

        String alreadyHeldSql =
                "SELECT COUNT(*) FROM User_Roles "
              + "WHERE user_id = ? AND role_id = ?";

        String conflictSql =
                "SELECT r.role_name "
              + "FROM User_Roles ur "
              + "INNER JOIN Roles r "
              + "ON ur.role_id = r.role_id "
              + "INNER JOIN Role_Conflicts rc "
              + "ON (rc.role_id_1 = ? AND rc.role_id_2 = ur.role_id) "
              + "OR (rc.role_id_2 = ? AND rc.role_id_1 = ur.role_id) "
              + "WHERE ur.user_id = ? "
              + "LIMIT 1";

        String insertSql =
                "INSERT INTO User_Roles (user_id, role_id, start_date) "
              + "VALUES (?, ?, CURDATE())";

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                return "Database error: could not connect.";
            }

            // Check 1: does the user already hold this role?
            try (PreparedStatement statement =
                         connection.prepareStatement(alreadyHeldSql)) {

                statement.setInt(1, userId);
                statement.setInt(2, roleId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        return "This user already holds that role.";
                    }
                }
            }

            // Check 2: does the new role conflict with a role already held?
            try (PreparedStatement statement =
                         connection.prepareStatement(conflictSql)) {

                statement.setInt(1, roleId);
                statement.setInt(2, roleId);
                statement.setInt(3, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return "Cannot assign this role: the user already holds \""
                                + resultSet.getString("role_name")
                                + "\", which conflicts with it.";
                    }
                }
            }
            
            // Check 3: is this role allowed for the user's department?
            String departmentSql =
                    "SELECT d.department_name, "
                  + "(SELECT COUNT(*) FROM Department_Roles x "
                  + "WHERE x.department_id = e.department_id) AS rule_count, "
                  + "(SELECT COUNT(*) FROM Department_Roles y "
                  + "WHERE y.department_id = e.department_id "
                  + "AND y.role_id = ?) AS allowed_count "
                  + "FROM Employee_Records e "
                  + "INNER JOIN Departments d "
                  + "ON d.department_id = e.department_id "
                  + "WHERE e.user_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(departmentSql)) {

                statement.setInt(1, roleId);
                statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {

                        if (resultSet.getInt("rule_count") > 0
                                && resultSet.getInt("allowed_count") == 0) {

                            return "Cannot assign this role: the \""
                                    + resultSet.getString("department_name")
                                    + "\" department is not allowed to use it.";
                        }
                    }
                }
            }
            
            // Both checks passed, so save the assignment
            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, userId);
                statement.setInt(2, roleId);
                statement.executeUpdate();
            }

            return null;

        } catch (SQLException e) {

            System.out.println("Error assigning role: " + e.getMessage());

            return "Database error: the role could not be assigned.";
        }
    }
}
