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

public class RoleConflictDAO {

    public List<RoleConflict> getAllRoleConflicts() {

        List<RoleConflict> conflicts = new ArrayList<>();

        String sql = "SELECT role_id_1, role_id_2 "
                   + "FROM Role_Conflicts";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                RoleConflict conflict = new RoleConflict(
                        resultSet.getInt("role_id_1"),
                        resultSet.getInt("role_id_2")
                );

                conflicts.add(conflict);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving role conflicts: "
                    + e.getMessage());
        }

        return conflicts;
    }
}
