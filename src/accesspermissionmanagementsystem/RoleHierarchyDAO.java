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

public class RoleHierarchyDAO {

    public List<RoleHierarchy> getAllRoleHierarchies() {

        List<RoleHierarchy> hierarchies = new ArrayList<>();

        String sql = "SELECT parent_role_id, child_role_id "
                   + "FROM Role_Hierarchy";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                RoleHierarchy hierarchy = new RoleHierarchy(
                        resultSet.getInt("parent_role_id"),
                        resultSet.getInt("child_role_id")
                );

                hierarchies.add(hierarchy);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving role hierarchy: "
                    + e.getMessage());
        }

        return hierarchies;
    }
}
