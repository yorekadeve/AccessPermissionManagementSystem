package accesspermissionmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public UserAccount login(
        String username,
        String password) {

    String passwordHash =
            PasswordUtil.hashPassword(password);

    String sql =
            "SELECT account_id, user_id, username, "
            + "password_hash, account_status, last_login "
            + "FROM User_Accounts "
            + "WHERE username = ? "
            + "AND password_hash = ?";

    try (Connection connection =
                 DatabaseConnection.getConnection();

         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setString(1, username);
        statement.setString(2, passwordHash);

        try (ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {

                String accountStatus =
                        resultSet.getString(
                                "account_status"
                        );

                if (!"ACTIVE".equalsIgnoreCase(
                        accountStatus)) {

                    UserAccount disabledAccount =
                            new UserAccount(
                                    resultSet.getInt("account_id"),
                                    resultSet.getInt("user_id"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password_hash"),
                                    resultSet.getString("account_status"),
                                    resultSet.getString("last_login")
                            );

                    return disabledAccount;
                }

                return new UserAccount(
                        resultSet.getInt("account_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("account_status"),
                        resultSet.getString("last_login")
                );
            }
        }

    } catch (SQLException e) {

        System.out.println(
                "Login error: "
                + e.getMessage()
        );
    }

    return null;
}
}