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

public class ResourceDAO {

    public List<Resource> getAllResources() {

        List<Resource> resources = new ArrayList<>();

        String sql = "SELECT resource_id, resource_name, description "
                   + "FROM Resources";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Resource resource = new Resource(
                        resultSet.getInt("resource_id"),
                        resultSet.getString("resource_name"),
                        resultSet.getString("description")
                );

                resources.add(resource);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving resources: "
                    + e.getMessage());
        }

        return resources;
    }
    
        public boolean addResource(Resource resource) {

        String sql = "INSERT INTO Resources "
                   + "(resource_name, description) "
                   + "VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, resource.getResourceName());
            statement.setString(2, resource.getDescription());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding resource: "
                    + e.getMessage());

            return false;
        }
    }

    public boolean updateResource(Resource resource) {

        String sql = "UPDATE Resources "
                   + "SET resource_name = ?, description = ? "
                   + "WHERE resource_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, resource.getResourceName());
            statement.setString(2, resource.getDescription());
            statement.setInt(3, resource.getResourceId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating resource: "
                    + e.getMessage());

            return false;
        }
    }

    public boolean deleteResource(int resourceId) {

        String sql = "DELETE FROM Resources "
                   + "WHERE resource_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, resourceId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting resource: "
                    + e.getMessage());

            return false;
        }
    }
}
