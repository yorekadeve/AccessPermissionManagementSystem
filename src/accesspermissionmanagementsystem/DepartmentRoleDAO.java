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

public class DepartmentRoleDAO {

    public List<DepartmentRole> getAllDepartmentRoles() {

        List<DepartmentRole> departmentRoles = new ArrayList<>();

        String sql = "SELECT department_id, role_id FROM Department_Roles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                DepartmentRole departmentRole = new DepartmentRole(
                        resultSet.getInt("department_id"),
                        resultSet.getInt("role_id")
                );

                departmentRoles.add(departmentRole);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving department roles: "
                    + e.getMessage());
        }

        return departmentRoles;
    }
}
