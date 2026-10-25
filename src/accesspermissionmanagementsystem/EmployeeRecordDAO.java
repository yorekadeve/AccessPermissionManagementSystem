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

public class EmployeeRecordDAO {

    public List<EmployeeRecord> getAllEmployeeRecords() {

        List<EmployeeRecord> employeeRecords = new ArrayList<>();

        String sql = "SELECT employee_id, user_id, department_id, "
                   + "job_title, employment_status "
                   + "FROM Employee_Records";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                EmployeeRecord employeeRecord = new EmployeeRecord(
                        resultSet.getInt("employee_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("department_id"),
                        resultSet.getString("job_title"),
                        resultSet.getString("employment_status")
                );

                employeeRecords.add(employeeRecord);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving employee records: "
                    + e.getMessage());
        }

        return employeeRecords;
    }
}
