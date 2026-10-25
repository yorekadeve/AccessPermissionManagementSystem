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

public class DashboardDAO {

    private int count(String sql) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Dashboard count error: " + e.getMessage());
        }

        return 0;
    }

    public int getTotalUsers() {
        return count("SELECT COUNT(*) FROM Users");
    }

    public int getTotalRoles() {
        return count("SELECT COUNT(*) FROM Roles");
    }

    public int getLoginsToday() {
        return count("SELECT COUNT(*) FROM Sessions "
                   + "WHERE login_time >= CURDATE()");
    }

    public int getDeniedLast24Hours() {
        return count("SELECT COUNT(*) FROM Access_Logs "
                   + "WHERE result = 'DENIED' "
                   + "AND action_time >= DATE_SUB(NOW(), INTERVAL 1 DAY)");
    }

    // Number of users who hold two roles that conflict with each other
    public int getUsersWithRoleClashes() {
        return count("SELECT COUNT(*) FROM ("
                   + "SELECT DISTINCT ur1.user_id "
                   + "FROM Role_Conflicts rc "
                   + "JOIN User_Roles ur1 ON ur1.role_id = rc.role_id_1 "
                   + "JOIN User_Roles ur2 ON ur2.role_id = rc.role_id_2 "
                   + "AND ur2.user_id = ur1.user_id) clashes");
    }
}
