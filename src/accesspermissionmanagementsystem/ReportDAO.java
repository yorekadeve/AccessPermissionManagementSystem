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
import java.sql.Timestamp;
import java.util.Date;

public class ReportDAO {

    public String generateReport(Date fromDate, Date toDate) {

        StringBuilder report = new StringBuilder();

        Timestamp fromTimestamp =
                new Timestamp(fromDate.getTime());

        Timestamp toTimestamp =
                new Timestamp(toDate.getTime());

        /*
         * The To Date is made inclusive by adding one day.
         * Therefore, selecting 2026-10-12 includes the
         * entire day of October 12.
         */
        java.sql.Timestamp endTimestamp;

        try {
            java.util.Calendar calendar =
                    java.util.Calendar.getInstance();

            calendar.setTime(toDate);

            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

            endTimestamp =
                    new Timestamp(calendar.getTimeInMillis());

        } catch (Exception e) {

            return "Unable to prepare report dates: "
                    + e.getMessage();
        }

        report.append("========================================\n");
        report.append("       ACCESS PERMISSION SYSTEM\n");
        report.append("              REPORT\n");
        report.append("========================================\n\n");

        report.append("REPORT PERIOD\n");
        report.append("----------------------------------------\n");
        report.append("From: ")
              .append(fromDate)
              .append("\n");

        report.append("To:   ")
              .append(toDate)
              .append("\n\n");

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            /*
             * 1. TOTAL SESSIONS
             */
            String sessionCountSQL =
                    "SELECT COUNT(*) AS total_sessions "
                  + "FROM Sessions "
                  + "WHERE login_time >= ? "
                  + "AND login_time < ?";

            int totalSessions = 0;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 sessionCountSQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        totalSessions =
                                resultSet.getInt("total_sessions");
                    }
                }
            }

            /*
             * 2. TOTAL ACCESS ATTEMPTS
             */
            String accessCountSQL =
                    "SELECT COUNT(*) AS total_access "
                  + "FROM Access_Logs "
                  + "WHERE action_time >= ? "
                  + "AND action_time < ?";

            int totalAccessAttempts = 0;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 accessCountSQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        totalAccessAttempts =
                                resultSet.getInt("total_access");
                    }
                }
            }

            /*
             * 3. ALLOWED ACCESS
             */
            String allowedSQL =
                    "SELECT COUNT(*) AS allowed_count "
                  + "FROM Access_Logs "
                  + "WHERE action_time >= ? "
                  + "AND action_time < ? "
                  + "AND result = 'ALLOWED'";

            int allowedAccess = 0;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 allowedSQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        allowedAccess =
                                resultSet.getInt("allowed_count");
                    }
                }
            }

            /*
             * 4. DENIED ACCESS
             */
            String deniedSQL =
                    "SELECT COUNT(*) AS denied_count "
                  + "FROM Access_Logs "
                  + "WHERE action_time >= ? "
                  + "AND action_time < ? "
                  + "AND result = 'DENIED'";

            int deniedAccess = 0;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 deniedSQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        deniedAccess =
                                resultSet.getInt("denied_count");
                    }
                }
            }

            /*
             * 5. UNIQUE USERS
             */
            String userCountSQL =
                    "SELECT COUNT(DISTINCT user_id) "
                  + "AS total_users "
                  + "FROM Access_Logs "
                  + "WHERE action_time >= ? "
                  + "AND action_time < ?";

            int totalUsers = 0;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 userCountSQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        totalUsers =
                                resultSet.getInt("total_users");
                    }
                }
            }

            /*
             * 6. BUILD SUMMARY
             */
            report.append("REPORT SUMMARY\n");
            report.append("----------------------------------------\n");

            report.append("Total Sessions:       ")
                  .append(totalSessions)
                  .append("\n");

            report.append("Total Users Active:   ")
                  .append(totalUsers)
                  .append("\n");

            report.append("Total Access Attempts:")
                  .append(" ")
                  .append(totalAccessAttempts)
                  .append("\n");

            report.append("Allowed Access:       ")
                  .append(allowedAccess)
                  .append("\n");

            report.append("Denied Access:        ")
                  .append(deniedAccess)
                  .append("\n\n");

            /*
             * 7. CALCULATE ACCESS SUCCESS RATE
             */
            double successRate = 0;

            if (totalAccessAttempts > 0) {

                successRate =
                        ((double) allowedAccess
                        / totalAccessAttempts) * 100;
            }

            report.append("Access Success Rate:  ")
                  .append(String.format(
                          "%.2f", successRate))
                  .append("%\n\n");

            /*
             * 8. SESSION DETAILS
             */
            report.append("SESSION DETAILS\n");
            report.append("----------------------------------------\n");

            String sessionsSQL =
                    "SELECT "
                  + "s.session_id, "
                  + "s.user_id, "
                  + "u.full_name, "
                  + "s.login_time, "
                  + "s.logout_time "
                  + "FROM Sessions s "
                  + "JOIN Users u "
                  + "ON s.user_id = u.user_id "
                  + "WHERE s.login_time >= ? "
                  + "AND s.login_time < ? "
                  + "ORDER BY s.login_time";

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 sessionsSQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    boolean foundSession = false;

                    while (resultSet.next()) {

                        foundSession = true;

                        report.append("Session ID: ")
                              .append(
                                  resultSet.getInt(
                                      "session_id"))
                              .append("\n");

                        report.append("User ID: ")
                              .append(
                                  resultSet.getInt(
                                      "user_id"))
                              .append("\n");

                        report.append("User Name: ")
                              .append(
                                  resultSet.getString(
                                      "full_name"))
                              .append("\n");

                        report.append("Login Time: ")
                              .append(
                                  resultSet.getTimestamp(
                                      "login_time"))
                              .append("\n");

                        report.append("Logout Time: ")
                              .append(
                                  resultSet.getTimestamp(
                                      "logout_time"))
                              .append("\n");

                        if (resultSet.getTimestamp(
                                "logout_time") == null) {

                            report.append("Status: ACTIVE\n");

                        } else {

                            report.append("Status: CLOSED\n");
                        }

                        report.append("----------------------------------------\n");
                    }

                    if (!foundSession) {

                        report.append(
                                "No sessions found for this period.\n");
                    }
                }
            }

            report.append("\n");

            /*
             * 9. ACCESS ACTIVITY DETAILS
             */
            report.append("ACCESS ACTIVITY\n");
            report.append("----------------------------------------\n");

            String activitySQL =
                    "SELECT "
                  + "al.log_id, "
                  + "u.full_name, "
                  + "al.session_id, "
                  + "o.operation_name, "
                  + "r.resource_name, "
                  + "al.target_record, "
                  + "al.action_time, "
                  + "al.result "
                  + "FROM Access_Logs al "
                  + "JOIN Users u "
                  + "ON al.user_id = u.user_id "
                  + "JOIN Operations o "
                  + "ON al.operation_id = o.operation_id "
                  + "JOIN Resources r "
                  + "ON al.resource_id = r.resource_id "
                  + "WHERE al.action_time >= ? "
                  + "AND al.action_time < ? "
                  + "ORDER BY al.action_time";

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 activitySQL)) {

                statement.setTimestamp(1, fromTimestamp);
                statement.setTimestamp(2, endTimestamp);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    boolean foundActivity = false;

                    while (resultSet.next()) {

                        foundActivity = true;

                        report.append("Log ID: ")
                              .append(
                                  resultSet.getInt(
                                      "log_id"))
                              .append("\n");

                        report.append("User: ")
                              .append(
                                  resultSet.getString(
                                      "full_name"))
                              .append("\n");

                        report.append("Session ID: ")
                              .append(
                                  resultSet.getInt(
                                      "session_id"))
                              .append("\n");

                        report.append("Operation: ")
                              .append(
                                  resultSet.getString(
                                      "operation_name"))
                              .append("\n");

                        report.append("Resource: ")
                              .append(
                                  resultSet.getString(
                                      "resource_name"))
                              .append("\n");

                        report.append("Target Record: ")
                              .append(
                                  resultSet.getString(
                                      "target_record"))
                              .append("\n");

                        report.append("Action Time: ")
                              .append(
                                  resultSet.getTimestamp(
                                      "action_time"))
                              .append("\n");

                        report.append("Result: ")
                              .append(
                                  resultSet.getString(
                                      "result"))
                              .append("\n");

                        report.append("----------------------------------------\n");
                    }

                    if (!foundActivity) {

                        report.append(
                                "No access activity found "
                              + "for this period.\n");
                    }
                }
            }

            report.append("\n");
            report.append("========================================\n");
            report.append("           END OF REPORT\n");
            report.append("========================================\n");

        } catch (SQLException e) {

            report.append("\nREPORT GENERATION ERROR\n");
            report.append("----------------------------------------\n");
            report.append(e.getMessage());
        }

        return report.toString();
    }
}
