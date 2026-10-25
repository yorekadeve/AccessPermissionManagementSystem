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

public class SessionDAO {

    public List<Session> getAllSessions() {

        List<Session> sessions = new ArrayList<>();

        String sql = "SELECT session_id, user_id, login_time, logout_time "
                   + "FROM Sessions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Session session = new Session(
                        resultSet.getInt("session_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("login_time"),
                        resultSet.getString("logout_time")
                );

                sessions.add(session);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving sessions: "
                    + e.getMessage());
        }

        return sessions;
    }
    
        public int createSession(int userId) {

        String sql = "INSERT INTO Sessions "
                   + "(user_id, login_time, logout_time) "
                   + "VALUES (?, NOW(), NULL)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, userId);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                try (ResultSet resultSet =
                        statement.getGeneratedKeys()) {

                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error creating session: "
                    + e.getMessage());
        }

        return 0;
    }
    
        public int getActiveSessionId(int userId) {

        String sql = "SELECT session_id "
                   + "FROM Sessions "
                   + "WHERE user_id = ? "
                   + "AND logout_time IS NULL "
                   + "ORDER BY login_time DESC "
                   + "LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("session_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving active session: "
                    + e.getMessage());
        }

        return 0;
    }
        
        public boolean closeSession(int sessionId) {

    String sql = "UPDATE Sessions "
               + "SET logout_time = NOW() "
               + "WHERE session_id = ? "
               + "AND logout_time IS NULL";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, sessionId);

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {

        System.out.println("Error closing session: "
                + e.getMessage());

        return false;
    }
}
}
