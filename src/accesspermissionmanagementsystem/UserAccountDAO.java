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

public class UserAccountDAO {

    public List<UserAccount> getAllUserAccounts() {

        List<UserAccount> accounts = new ArrayList<>();

        String sql = "SELECT account_id, user_id, username, password_hash, "
                   + "account_status, last_login FROM User_Accounts";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                UserAccount account = new UserAccount(
                        resultSet.getInt("account_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("account_status"),
                        resultSet.getString("last_login")
                );

                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user accounts: "
                    + e.getMessage());
        }

        return accounts;
    }
    
    public boolean disableAccount(int userId) {

    String sql = "UPDATE User_Accounts "
               + "SET account_status = 'DISABLED' "
               + "WHERE user_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {

        System.out.println("Error disabling account: "
                + e.getMessage());

        return false;
    }
}
    
    public boolean createAccount(
        int userId,
        String username,
        String password,
        String accountStatus) {

    String passwordHash =
            PasswordUtil.hashPassword(password);

    String sql =
            "INSERT INTO User_Accounts "
            + "(user_id, username, password_hash, account_status) "
            + "VALUES (?, ?, ?, ?)";

    try (Connection connection =
                 DatabaseConnection.getConnection();

         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);
        statement.setString(2, username);
        statement.setString(3, passwordHash);
        statement.setString(4, accountStatus);

        int rowsInserted =
                statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {

        System.out.println(
                "Error creating account: "
                + e.getMessage()
        );

        return false;
    }
}
}
