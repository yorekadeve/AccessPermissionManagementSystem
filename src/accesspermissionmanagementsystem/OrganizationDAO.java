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
import javax.swing.table.DefaultTableModel;

public class OrganizationDAO {

    public DefaultTableModel buildModel(String sql, String[] headers) {

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{}, headers) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Object[] row = new Object[headers.length];

                for (int i = 0; i < headers.length; i++) {
                    row[i] = resultSet.getString(i + 1);
                }

                model.addRow(row);
            }

        } catch (SQLException e) {
            System.out.println("Error loading table: " + e.getMessage());
        }

        return model;
    }

    private void bind(PreparedStatement statement, Object[] params)
            throws SQLException {

        for (int i = 0; i < params.length; i++) {

            Object value = params[i];

            if (value instanceof Integer) {
                statement.setInt(i + 1, (Integer) value);
            } else if (value instanceof java.sql.Date) {
                statement.setDate(i + 1, (java.sql.Date) value);
            } else {
                statement.setString(i + 1, (String) value);
            }
        }
    }

    // Returns null on success, or a readable message on failure.
    private String run(String sql, Object... params) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            bind(statement, params);
            statement.executeUpdate();

            return null;

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {
                return "That entry already exists.";
            }

            if (e.getErrorCode() == 1451) {
                return "Cannot delete this: other records depend on it.";
            }

            if (e.getErrorCode() == 1452) {
                return "Invalid reference: the related record does not exist.";
            }

            return "Database error: " + e.getMessage();
        }
    }

    private boolean exists(String sql, Object... params) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            bind(statement, params);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking record: " + e.getMessage());
            return false;
        }
    }

    // Departments
    public String addDepartment(String name, String description) {
        return run("INSERT INTO Departments (department_name, description) "
                 + "VALUES (?, ?)", name, description);
    }

    public String deleteDepartment(int departmentId) {
        return run("DELETE FROM Departments WHERE department_id = ?",
                   departmentId);
    }

    // Department roles
    public String addDepartmentRole(int departmentId, int roleId) {
        return run("INSERT INTO Department_Roles (department_id, role_id) "
                 + "VALUES (?, ?)", departmentId, roleId);
    }

    public String removeDepartmentRole(int departmentId, int roleId) {
        return run("DELETE FROM Department_Roles "
                 + "WHERE department_id = ? AND role_id = ?",
                   departmentId, roleId);
    }

    // Role hierarchy (parent = senior, inherits the child's permissions)
    public String addRoleHierarchy(int seniorId, int juniorId) {

        if (seniorId == juniorId) {
            return "A role cannot be senior to itself.";
        }

        if (exists("SELECT COUNT(*) FROM Role_Hierarchy "
                 + "WHERE parent_role_id = ? AND child_role_id = ?",
                   juniorId, seniorId)) {
            return "The reverse relationship already exists. "
                 + "That would create a loop.";
        }

        return run("INSERT INTO Role_Hierarchy (parent_role_id, child_role_id) "
                 + "VALUES (?, ?)", seniorId, juniorId);
    }

    public String removeRoleHierarchy(int seniorId, int juniorId) {
        return run("DELETE FROM Role_Hierarchy "
                 + "WHERE parent_role_id = ? AND child_role_id = ?",
                   seniorId, juniorId);
    }

    // Role conflicts (a pair is stored once; order does not matter)
    public String addRoleConflict(int roleA, int roleB) {

        if (roleA == roleB) {
            return "A role cannot conflict with itself.";
        }

        if (exists("SELECT COUNT(*) FROM Role_Conflicts "
                 + "WHERE (role_id_1 = ? AND role_id_2 = ?) "
                 + "OR (role_id_1 = ? AND role_id_2 = ?)",
                   roleA, roleB, roleB, roleA)) {
            return "That conflict already exists.";
        }

        return run("INSERT INTO Role_Conflicts (role_id_1, role_id_2) "
                 + "VALUES (?, ?)", roleA, roleB);
    }

    public String removeRoleConflict(int roleA, int roleB) {
        return run("DELETE FROM Role_Conflicts "
                 + "WHERE (role_id_1 = ? AND role_id_2 = ?) "
                 + "OR (role_id_1 = ? AND role_id_2 = ?)",
                   roleA, roleB, roleB, roleA);
    }

    // Employee records
    public String addEmployeeRecord(int userId, int departmentId,
                                    String jobTitle,
                                    java.sql.Date employmentDate,
                                    String status) {

        return run("INSERT INTO Employee_Records "
                 + "(user_id, department_id, job_title, "
                 + "employment_date, employment_status) "
                 + "VALUES (?, ?, ?, ?, ?)",
                   userId, departmentId, jobTitle, employmentDate, status);
    }

    public String deleteEmployeeRecord(int employeeId) {
        return run("DELETE FROM Employee_Records WHERE employee_id = ?",
                   employeeId);
    }

    // Reads the allowed values of an ENUM column, e.g. employment_status
    public List<String> getEnumValues(String table, String column) {

        List<String> values = new ArrayList<>();

        String sql = "SELECT COLUMN_TYPE FROM information_schema.COLUMNS "
                   + "WHERE TABLE_SCHEMA = DATABASE() "
                   + "AND LOWER(TABLE_NAME) = ? "
                   + "AND LOWER(COLUMN_NAME) = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, table.toLowerCase());
            statement.setString(2, column.toLowerCase());

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String type = resultSet.getString(1);

                    int start = type.indexOf('(');
                    int end = type.lastIndexOf(')');

                    if (start >= 0 && end > start) {

                        for (String part
                                : type.substring(start + 1, end).split(",")) {
                            values.add(part.replace("'", "").trim());
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error reading enum values: " + e.getMessage());
        }

        return values;
    }
}
